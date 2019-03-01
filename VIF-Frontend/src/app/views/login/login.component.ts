import { Component } from '@angular/core';
import { OauthService } from '../../services/oauth.service';
import { Router } from '@angular/router';
import { config } from '../../config/application.config';
import { first } from 'rxjs/operators';

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
    this.oauthService.login(this.username, this.password).pipe(first())
    .subscribe(
      data =>{
        this.router.navigate(['/dashboard']);
      },
      error => {
        this.router.navigate(['/login']);
        this.isLoginFail = true;
      }
    );
  }
}
