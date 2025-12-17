import React, { useState } from 'react'
import axios from 'axios'
const Adminadditerm = () => {
const[fields,setfields]=useState({file:"",name:"",price:"",catagary:"pizza",description:""})

const handlechanges=(e)=>{
  const{name,type,files,value}=e.target;
  setfields({
    ...fields,[name]:type==="file"? files[0]:value
  })
}


const handleadditerm=async()=>{
  if(fields.catagary=="" || fields.description=="" || fields.file=="" || fields.name=="" || fields.price==""){
    alert("fill all the fields")
    return
  }
const formData=new FormData();
formData.append("file",fields.file);
formData.append("name",fields.name);
formData.append("price",fields.price);
formData.append("catagary",fields.catagary)
formData.append("description",fields.description)

try{
const addurl=await axios.post("http://localhost:8080/iterms/additerm",formData,{
  headers:{"Content-Type": "multipart/form-data"},
})
alert(addurl.data.message);
}catch(err){
console.log("error adding iterm",err);
}

}



  return (
    <div className='menuecontainer'>
      <h1>Add Menue</h1>
    <input type="file" className='additerminput'  onChange={handlechanges} name='file' />
    <input type="text" placeholder='Name' className='additerminput' name='name' value={fields.name} onChange={handlechanges} />
    <input type="text"  placeholder='Price' className='additerminput' value={fields.price} onChange={handlechanges} name='price'/>
    <select name="catagary" className='additermselect' value={fields.catagary} onChange={handlechanges}>
      <option value="pizza">pizza</option>
      <option value="Burger">Burger</option>
      <option value="Sandwich">Sandwich</option>
      <option value="Pasta">Pasta</option>
      <option value="Fries">Fries</option>
      <option value="Frankie">Frankie</option>
      <option value="Desserts">Desserts</option>
    </select>
    <textarea placeholder='description-:' className='additermtextarea' value={fields.description} onChange={handlechanges} name='description'></textarea>
    <button className='button' onClick={handleadditerm}>Add iterm</button>
    </div>
  )
}

export default Adminadditerm
