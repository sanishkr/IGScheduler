package com.sns.igscheduler.Controller;

import com.sns.igscheduler.Cache.CacheHelper;
import com.sns.igscheduler.Tools.FileHelper;
import com.sns.igscheduler.model.Igpost;
import com.sns.igscheduler.model.UploadFileResponse;
import com.sns.igscheduler.service.FileStorageService;
import com.sns.igscheduler.service.IgpostService;
import com.sns.igscheduler.service.SchedulePostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Timer;

@RestController
@Api(value="IG Post", description="Operations related to scheduling IG posts")
public class IgpostController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private IgpostService igpostService;
    @Autowired
    private SchedulePostService schedulePostService;

    @ApiOperation(value = "Add a new Post", response = Iterable.class)
    @RequestMapping(value = "/create", method= RequestMethod.POST,produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    public String create(@RequestParam("image") MultipartFile image, @RequestParam String postdate, @RequestParam String posttime, @RequestParam String captions) throws ParseException {
        String imageName = fileStorageService.storeFile(image);
        String imageDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(imageName)
                .toUriString();

        Igpost igp = igpostService.create(imageName,postdate,posttime,captions);

        new UploadFileResponse(imageName, imageDownloadUri,
                image.getContentType(), image.getSize());

        schedulePostService.Schedulepost(igp);

        return igp.toString();
    }

    @RequestMapping(value = "/get/{ImageURL:.+}", method= RequestMethod.GET,produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @ApiOperation(value = "Get a particular Post", response = Iterable.class)
    public Igpost getIgpost(@PathVariable String ImageURL) {
        System.out.println(ImageURL);
        return igpostService.getByImage(ImageURL);
    }

    @RequestMapping(value = "/getAll", method= RequestMethod.GET,produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @ApiOperation(value = "get all Posts", response = Iterable.class)
    public List<Igpost> getAll(){
        return igpostService.getAll();
    }

    @RequestMapping(value = "/update/{ImageURL:.+}", method= RequestMethod.POST,produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @ApiOperation(value = "Update an existing Post", response = Iterable.class)
    public String update(@PathVariable String ImageURL, @RequestParam String postdate, @RequestParam String posttime, @RequestParam String captions) throws ParseException {
        Igpost igp = igpostService.update(ImageURL,postdate,posttime,captions);
        try {
            CacheHelper.PostCache.get(ImageURL).cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        schedulePostService.Schedulepost(igp);
        return igp.toString();
    }

    @RequestMapping(value = "/delete/{ImageURL:.+}", method= RequestMethod.DELETE,produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @ApiOperation(value = "Delete an existing Post", response = Iterable.class)
    public String delete(@PathVariable String ImageURL) {
        igpostService.delete(ImageURL);
        try {
            CacheHelper.PostCache.get(ImageURL).cancel();
            CacheHelper.PostCache.remove(ImageURL);
            FileHelper.deleteFileIfexist("./uploads/"+ImageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Deleted "+ImageURL;
    }

    @RequestMapping (value = "/deleteAll", method= RequestMethod.DELETE,produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @ApiOperation(value = "Delete all Posts", response = Iterable.class)
    public String deleteAll() {
        igpostService.deleteAll();
        try {
            for (Timer t: CacheHelper.PostCache.values()) {
                t.cancel();
            }
            CacheHelper.PostCache.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Deleted all records";
    }

    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
