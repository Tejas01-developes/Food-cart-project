import axios from 'axios';
import React, { useState } from 'react'
import { useNavigate } from 'react-router';

const Login = () => {
const navigate=useNavigate();
const[email,setemail]=useState("");
const[otp,setotp]=useState("");
const[steap,setsteap]=useState("email")
const[message,setmessage]=useState("");

const sendotp=async()=>{
if(email===""){
  setmessage("Enter the email to login in")
  return
}

try{
  const otpurl=await axios.post("http://localhost:8080/actions/login",{
    email
  })
if(otpurl.data.success){
  setmessage(otpurl.data.message)
  setsteap("otp");
}
}catch(err){
  setmessage("Error sending email")
  console.error(err)

}
}


const resendotp=()=>{
  sendotp()
  setsteap("otp")
}



const verifyotp=async()=>{
  try{

const verifyurl=await axios.post("http://localhost:8080/actions/verify",{
  email,
  otp
},{
  withCredentials:true
})
if(verifyurl.data.success){
  const access=verifyurl.data.access;
  localStorage.setItem("access",access);
  setmessage(verifyurl.data.message)
  navigate("/")
}
  }catch(err){
    setmessage("invalid otp");
  }
}




  return (
    <div className='logincontainer'>
   
   {

    steap=== "email" && (
      <div>

<input type="text"  placeholder='Enter Email id' value={email} onChange={(e)=>setemail(e.target.value)} />

<button onClick={sendotp}>Send otp</button>

      </div>


    )

   }

{

  steap === "otp" && (
<div>


<p>OTP send to {email}</p>

<input type="text" maxLength={6} placeholder='OTP' value={otp} onChange={(e)=>setotp(e.target.value)} />

<button onClick={verifyotp}>Verify OTP</button>

<button onClick={resendotp}>Rsend OTP</button>

</div>
  )
}








    </div>
  )
}

export default Login
