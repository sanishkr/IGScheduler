package com.sns.igscheduler.Controller;

import com.sns.igscheduler.model.Igpost;
import com.sns.igscheduler.service.IgpostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value="IG Post", description="Operations related to scheduling IG posts")
public class IgpostController {
    @Autowired
    private IgpostService igpostService;

    @ApiOperation(value = "Add a new Post", response = Iterable.class)
    @RequestMapping(value = "/create", method= RequestMethod.POST,produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    public String create(@RequestParam String imageURL, @RequestParam String postdate, @RequestParam String posttime, @RequestParam String captions) {
        Igpost igp = igpostService.create(imageURL,postdate,posttime,captions);
        return igp.toString();
    }

    @RequestMapping(value = "/get/{ImageURL}", method= RequestMethod.GET,produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @ApiOperation(value = "Get a particular Post", response = Iterable.class)
    public Igpost getIgpost(@RequestParam String Id) {
        return igpostService.getById(Id);
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

    @RequestMapping(value = "/update/{ImageURL}", method= RequestMethod.PUT,produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @ApiOperation(value = "Add a new Post", response = Iterable.class)
    public String update(@RequestParam String id, @RequestParam String imageURL, @RequestParam String postdate, @RequestParam String posttime, @RequestParam String captions) {
        Igpost igp = igpostService.update(id,imageURL,postdate,posttime,captions);
        return igp.toString();
    }

    @RequestMapping(value = "/delete/{ImageURL}", method= RequestMethod.DELETE,produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @ApiOperation(value = "Delete an existing Post", response = Iterable.class)
    public String delete(@RequestParam String imageurl) {
        igpostService.delete(imageurl);
        return "Deleted "+imageurl;
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
        return "Deleted all records";
    }
}
