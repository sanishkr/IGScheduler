package com.sns.igscheduler.Controller;

import com.sns.igscheduler.model.Igpost;
import com.sns.igscheduler.service.IgpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IgpostController {
    @Autowired
    private IgpostService igpostService;

    @RequestMapping("/create")
    public String create(@RequestParam String imageURL, @RequestParam String postdate, @RequestParam String posttime, @RequestParam String captions) {
        Igpost igp = igpostService.create(imageURL,postdate,posttime,captions);
        return igp.toString();
    }

    @RequestMapping("/get")
    public Igpost getIgpost(@RequestParam String Id) {
        return igpostService.getById(Id);
    }
    @RequestMapping("/getAll")
    public List<Igpost> getAll(){
        return igpostService.getAll();
    }
    @RequestMapping("/update")
    public String update(@RequestParam String id, @RequestParam String imageURL, @RequestParam String postdate, @RequestParam String posttime, @RequestParam String captions) {
        Igpost igp = igpostService.update(id,imageURL,postdate,posttime,captions);
        return igp.toString();
    }
    @RequestMapping("/delete")
    public String delete(@RequestParam String imageurl) {
        igpostService.delete(imageurl);
        return "Deleted "+imageurl;
    }
    @RequestMapping ("/deleteAll")
    public String deleteAll() {
        igpostService.deleteAll();
        return "Deleted all records";
    }
}
