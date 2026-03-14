package com.foodcart.project3.controller;

import com.foodcart.project3.repository.foodrepo;
import com.foodcart.project3.schemas.addproductschema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/iterms")
public class addfoodcontroller {
    @Autowired
    private foodrepo foodrepo;
    private final String folder="itermsimage/";

@PostMapping("/additerm")
    
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
public ResponseEntity<?>additerms(@RequestParam ("file") MultipartFile file,
@RequestParam("name") String name,@RequestParam("price")Double price,@RequestParam("catagary")String catagary,@RequestParam("description")String description) throws IOException {
  Path folderpath=Paths.get(folder);
  if(!Files.exists(folderpath)){
      Files.createDirectory(folderpath);
  }
  String filename=System.currentTimeMillis() + "_" + file.getOriginalFilename();
  Path filepath=folderpath.resolve(filename);
    Files.copy(file.getInputStream(),filepath, StandardCopyOption.REPLACE_EXISTING);


    addproductschema product=new addproductschema();
    product.setImageurl("/"+ folder + filename );
    product.setName(name);
    product.setPrice(price);
    product.setCategory(catagary);
    product.setDescription(description);
    product.setFoodid(System.currentTimeMillis());
    foodrepo.save(product);

    return ResponseEntity.ok(Map.of("success",true,"message","iterm added succesfully"));
}

@GetMapping("/getiterms")
    public ResponseEntity<?>getiterms(){
List<addproductschema> product=foodrepo.findAll();
return ResponseEntity.ok(Map.of("success",true,"iterm",product));
}






}
