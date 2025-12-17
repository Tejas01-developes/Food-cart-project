//package com.foodcart.project3.controller;
//
//import com.foodcart.project3.repository.cartrepo;
//import com.foodcart.project3.repository.foodrepo;
//import com.foodcart.project3.schemas.addproductschema;
//import com.foodcart.project3.schemas.cartschema;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@RequestMapping("/cart")
//@RestController
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//public class cartcontroller {
//    @Autowired
//    private cartrepo cartrepo;
//    @Autowired
//    private foodrepo foodrepo;
//
//    @PostMapping("/addcart")
//    public ResponseEntity<?>additermtocart(@RequestBody cartschema cartschema){
//
//        Optional<addproductschema> food=foodrepo.findById(cartschema.getFoodid());
//        if(food.isEmpty()){
//            return ResponseEntity.badRequest().body("Food not found");
//        }
//
//        Double price=food.get().getPrice();
//        cartschema exist=cartrepo.findByFoodidAndUserid(cartschema.getFoodid(),cartschema.getUserid());
//if(exist!=null){
//    int qty= exist.getQuantity() +1;
//
//    exist.setQuantity(qty);
//    exist.setTotalprice(price * qty);
//
//    cartrepo.save(exist);
//    return ResponseEntity.ok("quantity increased");
//}
//cartschema cart=new cartschema();
//cart.setCartid(UUID.randomUUID().toString().substring(0,8));
//cart.setUserid(cartschema.getUserid());;
//cart.setFoodid(cartschema.getFoodid());
//cart.setName(cartschema.getName());
//int quentity=1;
//cart.setQuantity(quentity);
//        cart.setPrice(price);
//cart.setTotalprice(price * quentity);
//cartrepo.save(cart);
//
//return ResponseEntity.ok(cart);
//    }
//
//
//
//
//
//    @PutMapping("/update")
//    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//    public ResponseEntity<?>updatequantity(@RequestBody cartschema cartschema) {
//        Optional<addproductschema> food=foodrepo.findById(cartschema.getFoodid());
//        if(food.isEmpty()){
//            return ResponseEntity.badRequest().body("Food not found");
//        }
//
//        Double price=food.get().getPrice();
//        cartschema exist = cartrepo.findByFoodidAndUserid(cartschema.getFoodid(), cartschema.getUserid());
//
//        if(exist == null){
//            return ResponseEntity.badRequest().body("iterm removed from cart");
//        }
//
//        exist.setQuantity(cartschema.getQuantity());
//
//        double newpricing= price * cartschema.getQuantity();
//        exist.setTotalprice(newpricing);
//
//        cartrepo.save(exist);
//        return ResponseEntity.ok(exist);
//
//    }
//
//    @DeleteMapping("/delete/{userid}/{foodid}")
//    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//    public ResponseEntity<?>deleteiterm(@PathVariable String userid,@PathVariable String foodid){
//        cartschema iterm= cartrepo.findByFoodidAndUserid(foodid,userid);
//
//        if(iterm == null){
//            return ResponseEntity.badRequest().body("iterm not found");
//        }
//
//        Optional<addproductschema> food=foodrepo.findById(foodid);
//        if(food.isEmpty()){
//            return ResponseEntity.badRequest().body("Food not found");
//        }
//
//        Double price=food.get().getPrice();
//
//
//        if(iterm == null){
//            return ResponseEntity.badRequest().body("iterm not found");
//        }
//
//        if(iterm.getQuantity() == 1){
//            cartrepo.delete(iterm);
//            return ResponseEntity.ok("iterm removed");
//        }
//
//        int qty= iterm.getQuantity() - 1;
//
//       iterm.setQuantity(qty);
//       iterm.setTotalprice(price * qty);
//       cartrepo.save(iterm);
//        return ResponseEntity.ok(iterm);
//
//    }
//
//
//    @GetMapping("/get/{userid}")
//    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//    public ResponseEntity<?> getcart(@PathVariable String userid){
//        List<cartschema> cartiterms=cartrepo.findByuserid(userid);
//
//        return ResponseEntity.ok(cartiterms);
//    }
//
//
//    @DeleteMapping("/clear/{userid}")
//    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//    public ResponseEntity<?> clearcart(@PathVariable String userid){
//        List<cartschema> all=cartrepo.findByuserid(userid);
//
//        cartrepo.deleteAll(all);
//        return ResponseEntity.ok("cart cleared");
//    }
//}
//
//
//
