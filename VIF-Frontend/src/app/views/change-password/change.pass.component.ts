import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { first } from 'rxjs/operators';
import { ResponseObject } from '../../models/Response.model';
import { MustMatch } from '../../helpers/function.share';
import { TokenResetPass } from '../../models/TokenResetPass';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';

@Component({
  selector: 'app-change-pass',
  templateUrl: 'change.pass.component.html',
  styleUrls: ['change.pass.component.scss']
})
export class ChangePasswordComponent {
  submitted = false;
  changePassForm: FormGroup;
  isChangedPass: boolean = false;
  username: string;

  constructor(private toastrService: ToastrService, private fb: FormBuilder, private userService: UserService, private route: ActivatedRoute) {
    this.createForm();
  }

  createForm() {
    this.changePassForm = this.fb.group({
      newPass: ['', [Validators.required, Validators.minLength(8)]],
      confirmNewPass: ['', [Validators.required, Validators.minLength(8)]],
      oldPass: ['', [Validators.required, Validators.minLength(8)]]
    }, {
        validator: [MustMatch('newPass', 'confirmNewPass')]
      });
  }

  get f() { return this.changePassForm.controls; }

  handleReset() { }
  handleExpire() { }
  handleLoad() { }
  handleSuccess(event) { }

  showSuccess(mes: string) {
    this.toastrService.success('', mes, {
      timeOut: config.timeoutToast
    });
  }

  showError(mes: string) {
    this.toastrService.error('', mes, {
      timeOut: config.timeoutToast
    });
  }

  onSubmit() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.changePassForm.invalid) {
      return;
    }
    // console.log("this.forgotForm.value: ", this.forgotForm.value.username);
    var tokenResetPass: TokenResetPass = new TokenResetPass();
    var currentUser = localStorage.getItem("currentUser");
    if (currentUser) {
      var username = JSON.parse(currentUser).username;
      tokenResetPass.username = username;
    }

    tokenResetPass.newPass = this.changePassForm.value.newPass;
    tokenResetPass.oldPass = this.changePassForm.value.oldPass;

    this.userService.changePassword(tokenResetPass).pipe(first()).subscribe((res: any) => {
      var response: ResponseObject = res;
      if (response.code === 200) {
        this.isChangedPass = true;
        localStorage.clear();
      }else{
        this.showError("Đổi mật khẩu không thành công! Vui lòng kiểm tra thông tin chính xác trước!");
      }
    });

  }
}
