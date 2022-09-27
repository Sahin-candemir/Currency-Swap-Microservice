import React from 'react';
import axios from 'axios';
import { useState } from 'react';
import { Alert } from 'bootstrap';
   
function UserLoginPage() {
    const [user,setUser]= useState({username:"",password:""});

    const handleUsername= (event) => {
        setUser({...user,
            username:event.target.value});
    }  
    const handlePassword= (event) => {
        setUser({...user,
            password:event.target.value});
    }

      const handleButton = (event) => {
  
          event.preventDefault();
          //console.log(user);
         
          axios.post('/a-service/v1/auth/login', user)
              .then(response => {
              localStorage.setItem('token', response.data.accessToken);
              localStorage.setItem('currentUser', response.data.userId);
              localStorage.setItem('username',response.data.username);
              window.location.href="/";
              })
              .catch(error => alert("Username or password is wrong"))    
      }
  
    return (
        <div className='container'>
            <form>
                    <h1 className='text-center'>Login</h1>
                    <div className='form-group'>
                        <label>Username</label>
                        <input className='form-control' name="username" onChange = {(i) => handleUsername(i)}/>
                    </div>
                    <div>
                        <label>Password</label>
                        <input className='form-control' name="password" type="password" onChange = {(i) => handlePassword(i)}/>
                    </div>
                    <button className='btn btn-primary' onClick={handleButton}>Login</button>

                </form>
        </div>
    )

}

export default UserLoginPage;