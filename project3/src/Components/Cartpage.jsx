import axios from 'axios'
import React, { useEffect, useState } from 'react'

const Cartpage = () => {

const[user,setuser]=useState()
const[iterm,setiterm]=useState([])
const[loading,setloading]=useState(true)
const[quantity,setquantity]=useState({})
const[qrurl,setqrurl]=useState(null)
useEffect(()=>{

const token=localStorage.getItem("access");

if(token == null){
    setuser(null)
    setloading(false)
    return
}

axios.get("http://localhost:8080/actions/getlogged",{
    headers:{
        Authorization:`Bearer ${token}`
    },
    // withCredentials:true
})

.then(resp=>{setuser(resp.data)

return axios.get(`http://localhost:8080/cart/get/${resp.data.userid}`,{
    headers:{
        Authorization:`Bearer ${token}`
    },
    // withCredentials:true
})
})

.then(cartresp=>{

    setiterm(Array.isArray(cartresp.data) ? cartresp.data : []);
    const newqty={};
   (Array.isArray (cartresp.data) ? cartresp.data: []).forEach(i=>{
    if(i && i.foodid !=null){
        newqty[i.foodid]=i.quantity ?? 1;
    }
        
    })
    setquantity(newqty)
    console.log("cart iterm:" ,cartresp.data)
})
.catch(err=>{
    console.log("Error fetching cart",err);
})
.finally(()=>{
    setloading(false)
})


},[])






const increaseqty=async(foodid)=>{
    const newqty=(quantity[foodid] || 0) +1;
    setquantity(prev=>({
        ...prev,[foodid]:newqty
    }))

const token=localStorage.getItem("access");
await axios.put("http://localhost:8080/cart/update",{
    userid:user.userid,
    foodid,
    quantity:newqty
},{
    headers:{Authorization: `Bearer ${token}`},
    // withCredentials:true
})
}


const decreaseqty=async(foodid)=>{
    const newqty=(quantity[foodid] || 0) - 1
    const token=localStorage.getItem("access");

    if(newqty <= 0){
        try{
      await axios.delete(`http://localhost:8080/cart/delete/${user.userid}/${foodid}`,{
        headers:{Authorization:`Bearer ${token}`}
      });
      setquantity(prev=>{
        const update={...prev};
        delete update[foodid];
        return update;
        })

setiterm(prev=>prev.filter(i =>i.foodid !== foodid));

    }catch(err){
        console.log("Error removing item", err);
        alert("Failed to remove item");
    }
return
}
try{
await axios.put("http://localhost:8080/cart/update",{
userid:user.userid,
foodid:foodid,
quantity:newqty
},{
    headers:{Authorization:`Bearer ${token}`},
    // withCredentials:true
})
      
   setquantity(prev=>({

...prev,[foodid]:newqty
   }))    
}catch(err){
    console.log("Error updating quantity", err);
    alert("Failed to update quantity");
}
}




const total=iterm.reduce((sum,item)=>{
    return sum + item.itermprice * (quantity[item.foodid] || item.quantity);
},0);     //0 is starting value of sum 





const handlebuy=async(totalamount)=>{
if(!iterm || iterm.length === 0){
    alert("nothing added to cart")
    return
}


const token=localStorage.getItem("access");

const response=await axios.post("http://localhost:8080/buynow/payment",{
    amount:totalamount,
    userid:user.userid,
    quantity:quantity,
    totalprice:total,
},{
    responseType:"arraybuffer",
    headers:{Authorization:`Bearer ${token}`}
})

const blob=new Blob([response.data],{type:"image/png"});
const url=URL.createObjectURL(blob)
setqrurl(url);


}













  return (
    <div className='cartcontainer'>
    <div className='cartpage'>

<h1>Your Cart Iterm</h1>

{
!iterm || iterm.length===0 ? (
    <p>No iterms added</p>
):(

iterm.map(item=>(

<div key={item.cartid}>
    
<p> <b> Name:</b>{item.itermname}</p>

<p><b>Price</b>{item.itermprice}</p>
<p><b>Total:</b>{item.itermprice * (quantity[item.foodid] || item.quantity)}</p>
<p><button onClick={()=>decreaseqty(item.foodid)}>-</button>{quantity[item.foodid || item.quantity]}<button onClick={()=>increaseqty(item.foodid)}>+</button></p>
</div>
))
)
}
    </div>


    <div>
        <h3>Total:₹{total}</h3>
        <button onClick={()=>handlebuy(total)}>Buy Now</button>
    </div>

{
    qrurl && (
<div>

    <img src={qrurl} alt="Payment QR" />
    <button onClick={()=>setqrurl(null)}>Close</button>
</div>


    )
}



    </div>
  )
}

export default Cartpage
