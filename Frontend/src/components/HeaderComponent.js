import React, { Component } from 'react'

export default class HeaderComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
          
        }
    }
   

     onClickLogout(){
      localStorage.removeItem('token');
      localStorage.removeItem('currentUser');
      localStorage.removeItem('username');
      console.log(localStorage.getItem('token'));
      //window.location.reload(1);
    }
//onClick={this.onClickLogout}
  render() {
    
    return (
            <nav className='navbar navbar-expand-md navbar-dark bg-dark'>
                <a className='navbar-brand' href='/'>Home</a>
                <a className='nav-link' href='/exchange'>Exchange</a>
                <a style={{color:"white",fontSize:"25px", position:"absolute", right:"150px"}} className="nav-item nav-link disabled" href="/" >{localStorage.getItem('username')}</a>
                <a style={{color:"white", position:"absolute", right:"100px"}} className="btn btn-outline-success my-2" href="/login" >Login</a>
                <a style={{color:"white", position:"absolute", right:"20px"}} onClick ={this.onClickLogout} className="btn btn-outline-danger my-2" href="/login" >Logout</a>
            </nav>
    )
  }
}
