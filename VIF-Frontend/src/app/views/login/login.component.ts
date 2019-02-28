import { Component } from '@angular/core';
import { OauthService } from '../../services/oauth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: 'login.component.html'
})
export class LoginComponent {
  username: string;
  password: string;
  isLoginFail: boolean=false; 
  constructor(private router: Router, private oauthService:OauthService){
  }

  login(){
    let result = this.oauthService.login(this.username, this.password);
    if(result){
      this.router.navigate(['/dashboard']);
    }else{
      this.router.navigate(['/login']);
      this.isLoginFail = true;
    }
  }
}
