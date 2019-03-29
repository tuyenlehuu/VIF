import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { first } from 'rxjs/operators';
import { ResponseObject } from '../../models/Response.model';

@Component({
  selector: 'app-forgot-pass',
  templateUrl: 'forgot.pass.component.html',
  styleUrls: ['forgot.pass.component.scss']
})
export class ResetPassComponent {
  submitted = false;
  forgotForm: FormGroup;
  siteKey: any = "6LenwJoUAAAAAO_76fBKd1KA4jRS7m4IFI5yH3GN";
  secretKey = "6LenwJoUAAAAAKGA9FieevPVcwrf3YFZFDmPpXT6";
  size = 12;
  isAfterReset: boolean = false;

  constructor(private fb: FormBuilder, private userService:UserService) { this.createForm() }

  createForm() {
    this.forgotForm = this.fb.group({
      username: ['', Validators.required],
      recaptcha: ['', Validators.required]
    });
  }

  get f() { return this.forgotForm.controls; }

  handleReset() { }
  handleExpire() {
    
  }
  handleLoad() { }
  handleSuccess(event) { console.log(event); }

  onSubmit() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.forgotForm.invalid) {
      return;
    }
    // console.log("this.forgotForm.value: ", this.forgotForm.value.username);
    this.userService.prepareResetPassword(this.forgotForm.value.username).pipe(first()).subscribe((res:any) =>{
      var response: ResponseObject = res;
      if(response.code === 200){
        this.isAfterReset = true;
        localStorage.setItem("doResetPass", "true");
      }
    });
    
  }
}
