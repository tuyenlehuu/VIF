import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { first } from 'rxjs/operators';
import { ResponseObject } from '../../models/Response.model';
import { MustMatch } from '../../helpers/function.share';
import { TokenResetPass } from '../../models/TokenResetPass';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reset-change-pass',
  templateUrl: 'change.reset.pass.component.html',
  styleUrls: ['change.reset.pass.component.scss']
})
export class ChangeResetPassComponent {
  submitted = false;
  changePassForm: FormGroup;
  isChangedPass: boolean = false;
  token: string;
  username:string;

  constructor(private fb: FormBuilder, private userService: UserService, private route:ActivatedRoute) { 
    this.route.queryParams.subscribe(params => {
      this.username = params['username'];
      this.token = params['token'];
  });
    
    this.createForm() 
  }

  createForm() {
    this.changePassForm = this.fb.group({
      newPass: ['', [Validators.required, Validators.minLength(8)]],
      confirmNewPass: ['', [Validators.required, Validators.minLength(8)]],
    }, {
        validator: [MustMatch('newPass', 'confirmNewPass')]
      });
  }

  get f() { return this.changePassForm.controls; }

  handleReset() { }
  handleExpire() { }
  handleLoad() { }
  handleSuccess(event) { }

  onSubmit() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.changePassForm.invalid) {
      return;
    }
    // console.log("this.forgotForm.value: ", this.forgotForm.value.username);
    var tokenResetPass:TokenResetPass = new TokenResetPass();
    tokenResetPass.username = this.username;
    tokenResetPass.token = this.token;
    tokenResetPass.newPass = this.changePassForm.value.newPass;
    this.userService.resetPassword(tokenResetPass).pipe(first()).subscribe((res: any) => {
      var response: ResponseObject = res;
      if (response.code === 200) {
        this.isChangedPass = true;
      }
    });

  }
}
