package com.foodcart.project3.service;

import com.foodcart.project3.repository.cartrepo;
import com.foodcart.project3.repository.orderhistoryrepo;
import com.foodcart.project3.schemas.cartschema;
import com.foodcart.project3.schemas.orderhistory;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buynow")
public class payment {
    @Autowired
    private orderhistoryrepo orderhistoryrepo;
    @Autowired
    private cartrepo cartrepo;
@Transactional
    @PostMapping("/payment")
    @CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
    public ResponseEntity<byte[]>generateqr(@RequestBody Map<String,Object> data) throws IOException, WriterException {
        int amount=(int) data.get("amount");
        String userid= (String) data.get("userid");
        List<cartschema> cartiterms=cartrepo.findByUserid(userid);
        for (cartschema iterm:cartiterms){

            orderhistory history=new orderhistory();
            history.setUserid(iterm.getUserid());
            history.setFoodid(iterm.getFoodid());
            history.setQuantity(iterm.getQuantity());
            history.setItermname(iterm.getItermname());
            history.setTotalprice(iterm.getQuantity() * iterm.getItermprice());
            orderhistoryrepo.save(history);


        }
        cartrepo.deleteByUserid(userid);
        String paymenttext= "upi://pay?pa=your-upi-id@bank&pn=FoodCart&am=" + amount;
        BitMatrix matrix=new MultiFormatWriter().encode(paymenttext, BarcodeFormat.QR_CODE,300,300);

        ByteArrayOutputStream out= new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix,"PNG",out);

        return ResponseEntity.ok().header("Content-Type","image/png")
                .body(out.toByteArray());
    }



}
