package com.sns.igscheduler.service;

import com.sns.igscheduler.Repository.IgpostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sns.igscheduler.model.Igpost;

import java.util.List;

@Service
public class IgpostService {

    @Autowired
    private IgpostRepo igpostRepo;

    /**Create
     *
     * @param id
     * @param imageurl
     * @param postdate
     * @param posttime
     * @param captions
     * @return
     */
    public Igpost create(String imageurl,String postdate,String posttime,String captions){
        return igpostRepo.save(new Igpost(imageurl,postdate,posttime,captions));
    }

    /**Retrieve
     *
     * @return
     */
    public List<Igpost> getAll(){
        return igpostRepo.findAll();
    }
    public Igpost getById(String id) {
        return igpostRepo.findByid(id);
    }

    /**Update
     *
     * @param Id
     * @param imageurl
     * @param postdate
     * @param posttime
     * @param captions
     * @return
     */
    public Igpost update(String Id, String imageurl,String postdate,String posttime,String captions) {
        Igpost igp = igpostRepo.findByid(Id);
        igp.setImageurl(imageurl);
        igp.setPostdate(postdate);
        igp.setPosttime(posttime);
        igp.setCaptions(captions);
        return igpostRepo.save(igp);
    }

    /**Delete
     *
     */
    public void deleteAll() {
        igpostRepo.deleteAll();
    }
    public void delete(String Id) {
        Igpost p = igpostRepo.findByid(Id);
        igpostRepo.delete(p);
    }
}
