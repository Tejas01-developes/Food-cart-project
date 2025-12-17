import React, { useEffect, useState } from 'react'
import {useNavigate} from 'react-router-dom'
import axios, { AxiosHeaders } from 'axios'



const Home = () => {
    
    const navigate=useNavigate();
const[profile,setprofile]=useState(false);
const[user,setuser]=useState(null);
const[iterm,setiterm]=useState([])
const[cartiterm,setcartiterm]=useState([])
const[catagary,setcatagary]=useState("All")

const isincart=(foodid)=>{
if(!cartiterm || cartiterm.length === 0)return false;
return cartiterm.some(iterm=>iterm.foodid === foodid);
}




const handleprofileclick=()=>{
    setprofile(!profile);
}

const handleprofilelogin=()=>{

    navigate("/login")
}


useEffect(()=>{
    const token=localStorage.getItem("access");
    if(!token){
        setuser(null);
        return
    }

axios.get("http://localhost:8080/actions/getlogged",{
    headers:{
        Authorization:`Bearer ${token}`
    },
    // withCredentials:true
})

.then(resp=>{
    setuser(resp.data)
})

.catch(err=>{
    console.log("user not logged in")
    setuser(null)
})




},[])













const images=[
    // burger
    "https://thumbs.dreamstime.com/b/tasty-burger-french-fries-fire-close-up-home-made-flames-137249900.jpg"  ,

// fries
"https://static.vecteezy.com/system/resources/thumbnails/039/655/363/small/ai-generated-freshly-fried-gourmet-french-fries-a-crunchy-and-unhealthy-snack-generated-by-ai-photo.jpg",


// pizza
"https://png.pngtree.com/png-vector/20241211/ourmid/pngtree-authentic-italian-pizza-with-cheese-and-fresh-vegetable-toppings-png-image_14714611.png",

// sandwich
"https://img.freepik.com/free-photo/front-view-delicious-ham-sandwiches-wooden-board-dark-surface_179666-34425.jpg?semt=ais_hybrid&w=740&q=80"

]

const[current,setcurrent]=useState(0)
useEffect(()=>{
    const timer=setInterval(()=>{
setcurrent((prev)=>(prev+1) % images.length)
    },2000)
    return ()=>clearInterval(timer)
},[images.length])




useEffect(()=>{
    axios.get("http://localhost:8080/iterms/getiterms")

    .then(resp=>{
        console.log(resp.data.iterm);
        setiterm(resp.data.iterm)
    })
    .catch(err=>console.error(err))
},[])



//     const fetchall=()=>{axios.get("http://localhost:8080/iterms/getiterms")

//     .then(resp=>{
//         console.log(resp.data);
//         setiterm(resp.data)
//     })
//     .catch(err=>console.error(err))
// }





// useEffect(()=>{
//     fetchall();
// },[])




const handlecatagary=async(category)=>{
setcatagary(category);

// if(catagary === "All"){
//     fetchall();
//     return
// }

axios.get(`http://localhost:8080/actions/iterms/${category}`)
.then(resp=>setiterm(Array.isArray(resp.data) ? resp.data : resp.data.itermss || []))
// .then(resp=>setiterm(resp.data))
.catch(err =>console.log(err))





}







const handleaddclick=async(item)=>{
if(!user){
    alert("please login first")
    return
}

const token=localStorage.getItem("access");

try{
const addcarturl=await axios.post("http://localhost:8080/cart/addcart",{
    userid:user.userid,
    foodid:item.foodid,
    
},{
    headers:{Authorization:`Bearer ${token}`},
    // withCredentials:true
})

const updateiterm=addcarturl.data;

    if(!updateiterm || !updateiterm.foodid){
        console.log("invalid updates",updateiterm)
    }
setcartiterm(prev=>[...prev,{
    foodid:item.foodid,
    itermname:item.name,
    itermprice:item.price,
    quantity:1
}])


}catch(err){
    console.log("error adding to cart",err);
    alert("Failed to add iterm to cart");
}



}





const handlecartlogo=()=>{
    navigate("/cart")
    return
}

const gotocart=()=>{
    navigate("/cart")
}


useEffect(()=>{
if(!user) return

const token=localStorage.getItem("access")
axios.get(`http://localhost:8080/cart/get/${user.userid}`,{
    headers:{Authorization:`Bearer ${token}`}
})
.then(resp=>{
    setcartiterm(resp.data || [])
    console.log("user cart" ,resp.data)
})
.catch(err=>{

    console.log("Error fetching user cart",err)
})
},[user])











  return (
    <div>
    
    <header className='homeheader'>
<h1 className='heading'>Food Cart Project</h1>
<img src="https://static.vecteezy.com/system/resources/previews/027/381/351/non_2x/shopping-cart-icon-shopping-trolley-icon-shopping-cart-logo-container-for-goods-and-products-economics-symbol-design-elements-basket-symbol-silhouette-retail-design-elements-vector.jpg" alt="" className='cartlogo' onClick={handlecartlogo} />
<img src="https://img.icons8.com/ios-glyphs/60/000000/user--v1.png" alt="profile-icon" className='profileicon' onClick={handleprofileclick}/> 

{
profile && (
    <div className='profiledropdown'>

<p><strong>Email:</strong>{user ? user.email : ""}</p>
{
    user ? (

        <button className='profilebutton' onClick={()=>{
            localStorage.removeItem("access");
            axios.post("http://localhost:8080/actions/logout",{},{
                withCredentials:true
            }).then(()=>window.location.reload())
        }}>Logout</button>
    ) : (
        <button className='profilebutton' onClick={handleprofilelogin}>Login</button>
    )
}

    </div>
)

}
    </header>


    <div className='sliders'>
    <img src={images[current]} alt="food" className='foodimages' />
    </div>













        <div className='catagorycontainer'>

      




    <div className='catagorydiv'onClick={() => handlecatagary("burger")}>
        <img src="https://thumbs.dreamstime.com/b/tasty-burger-french-fries-fire-close-up-home-made-flames-137249900.jpg" alt="" className='catagoryimages'/>
        <p className='catagorylable'>Burger</p>
        </div>


        <div className='catagorydiv' onClick={() => handlecatagary("fries")}>
        <img src="https://static.vecteezy.com/system/resources/thumbnails/039/655/363/small/ai-generated-freshly-fried-gourmet-french-fries-a-crunchy-and-unhealthy-snack-generated-by-ai-photo.jpg" alt="" className='catagoryimages'/>
        <p className='catagorylable'>Fries</p>
        </div>


        <div className='catagorydiv' onClick={() => handlecatagary("pizza")}>
        <img src="https://png.pngtree.com/png-vector/20241211/ourmid/pngtree-authentic-italian-pizza-with-cheese-and-fresh-vegetable-toppings-png-image_14714611.png" alt="" className='catagoryimages'/>
        <p className='catagorylable'>Pizza</p>
        </div>

        <div className='catagorydiv' onClick={() => handlecatagary("sandwhich")}>
        <img src="https://img.freepik.com/free-photo/front-view-delicious-ham-sandwiches-wooden-board-dark-surface_179666-34425.jpg?semt=ais_hybrid&w=740&q=80" alt="" className='catagoryimages'/>
        <p className='catagorylable'>Sandwhich</p>
        </div>

        <div className='catagorydiv' onClick={() => handlecatagary("Pasta")}>
        <img src="https://www.indianveggiedelight.com/wp-content/uploads/2022/12/white-sauce-pasta-featured.jpg" alt="" className='catagoryimages'/>
        <p className='catagorylable'>Pasta</p>
        </div>

        <div className='catagorydiv' onClick={() => handlecatagary("frankie")}>
        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTllkjDPZhTbvnVYSwaR_WaYR29Q4n6Mlm8Nw&s" alt="" className='catagoryimages'/>
        <p className='catagorylable'>Frankie</p>
        </div>

        <div className='catagorydiv' onClick={() => handlecatagary("dessert")}>
        <img src="https://t3.ftcdn.net/jpg/00/96/19/86/360_F_96198695_oyJg0I7ELpXI6608FI942PX9LlRRyEnd.jpg" alt="" className='catagoryimages'/>
        <p className='catagorylable'>Dessert</p>
    </div>
   
    </div>



<div className='itermscontainer'>
{

iterm.map((item)=>(
<div key={item.foodid} className='itermcard'>
<img src={`http://localhost:8080${item.imageurl}`} alt={item.name} className='itermimage'/>
<h3 className='itermname'>{item.name}</h3>
<p className='itermdescription'>{item.description}</p>
<div className='itermfooter'>
<p className='itermprice'>{item.price}</p>
{
   isincart(item.foodid) ? (
<div>
<button onClick={gotocart}>Go to cart</button>
</div>

    ) :(
        <button className='addbutton' onClick={()=>handleaddclick(item)}>ADD +</button>
    )
}

</div>

</div>
))
}
</div>
    </div>
  )
}


export default Home
