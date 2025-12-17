package com.foodcart.project3.controller;

import com.foodcart.project3.repository.cartrepo;
import com.foodcart.project3.repository.foodrepo;
import com.foodcart.project3.repository.userrepo;
import com.foodcart.project3.schemas.addproductschema;
import com.foodcart.project3.schemas.cartschema;
import com.foodcart.project3.schemas.userschema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.engine.ProcessingInstructionStructureHandler;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class cartcontrollerr {
    @Autowired
    private cartrepo cartrepo;
    @Autowired
    private foodrepo foodrepo;
    @Autowired
    private userrepo userrepo;

    @PostMapping("/addcart")
    public ResponseEntity<?>addingcart(@RequestBody cartschema req){

        Optional<addproductschema> fooditerms=foodrepo.findById(req.getFoodid());
        if(fooditerms.isEmpty()){
            return ResponseEntity.badRequest().body("no food iterm");
        }

        double price=fooditerms.get().getPrice();

        cartschema iterms= cartrepo.findByFoodidAndUserid(req.getFoodid(), req.getUserid());
        if(iterms != null){
            iterms.setQuantity((long) (iterms.getQuantity() +1));
            iterms.setItermprice((long) price);
            iterms.setTotalprice(iterms.getItermprice() * iterms.getQuantity());
            cartrepo.save(iterms);
            return ResponseEntity.ok(iterms);
        }

        cartschema newiterm= new cartschema();
        newiterm.setFoodid(req.getFoodid());
        newiterm.setUserid(req.getUserid());
        newiterm.setItermname(fooditerms.get().getName());
        newiterm.setQuantity(1L);
        newiterm.setItermprice((long) (price));
        newiterm.setTotalprice((long) (price) * newiterm.getQuantity());

        cartrepo.save(newiterm);

        return ResponseEntity.ok(newiterm);



    }

    @GetMapping("/get/{userid}")
    public ResponseEntity<?>getuser(@PathVariable String userid){
        List<cartschema> getuser=cartrepo.findByUserid(userid);

        return ResponseEntity.ok(getuser);
    }

    @PutMapping("/update")
    public ResponseEntity<?>updatequantity(@RequestBody cartschema req){
        Optional<addproductschema> food=foodrepo.findById(req.getFoodid());
        if(food.isEmpty()){
            return ResponseEntity.badRequest().body("food not found");
        }

        double price=food.get().getPrice();
        cartschema quantity= cartrepo.findByFoodidAndUserid(req.getFoodid(), req.getUserid());
        if(quantity == null){
            return ResponseEntity.badRequest().body("iterm removed from cart");
        }

        quantity.setQuantity(req.getQuantity());
        quantity.setTotalprice((long) (price * req.getQuantity()));
        cartrepo.save(quantity);
        return ResponseEntity.ok(quantity);
    }


    @DeleteMapping("/delete/{userid}/{foodid}")
    public ResponseEntity<?>deleteitermfromcart(@PathVariable String foodid,@PathVariable String userid){
       cartschema deleteiterm=cartrepo.findByFoodidAndUserid(foodid,userid);
       if(deleteiterm == null){
           return ResponseEntity.badRequest().body("iterm is not present in the cart");
       }
       Optional<addproductschema> food=foodrepo.findById(foodid);
       if(food.isEmpty()){
           return ResponseEntity.badRequest().body("food not found");
       }

       double price=food.get().getPrice();

       if(deleteiterm.getQuantity() == 1){
           cartrepo.delete(deleteiterm);
           return ResponseEntity.ok("iterm removed");
       }
       Long qty=  deleteiterm.getQuantity()-1;

       deleteiterm.setQuantity(qty);
       deleteiterm.setTotalprice((long) (price * qty));
       cartrepo.save(deleteiterm);
       return ResponseEntity.ok(deleteiterm);


    }






}
