import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ChangeInfoService } from '../../services/ChangeInfo.service';
import { User } from '../../models/User.model';
import { forkJoin } from 'rxjs';
import { UserService } from '../../services/user.service';
import { Customer } from '../../models/Customer.model';
import { Pager } from '../../models/Pager';
import { first } from 'rxjs/operators';

@Component({
  templateUrl: 'change.info.component.html',
})
export class ChangeInfoComponent implements OnInit {
  changeInfoForm: FormGroup;
  submitted = false;
  user: User = new User();
  localUrlAvatar: any[];
  isChangedInfo: boolean = false;
  selectFileAvatar: File;
  id: number;
  email: string;
  users: User[] = [];
  customer: Customer;

  constructor(private toastrService: ToastrService, private fb: FormBuilder, private router: Router, private changeInfoService: ChangeInfoService, private userService: UserService, private route: ActivatedRoute) {
    this.creatForm();
  }
  ngOnInit(): void {
    var x: string;
    var pager: Pager = new Pager();
    var currentUser = localStorage.getItem("currentUser");
    var u = JSON.parse(currentUser);
    x = u.username.toString();
    this.user.username = x;
    pager.page = 1;
    this.userService.getUsersByCondition(this.user, pager).pipe(first()).subscribe((res: any) => {
      this.users = res.data;
      this.user = this.users[0];
      this.customer = this.user.customer;
      this.creatForm();
    });
  }
  creatForm() {
    this.changeInfoForm = this.fb.group({
      identityAvatar: [''],
      email: [this.user.email, Validators.required],
    });
  }
  saveUser(user: User) {
    this.user.email = this.changeInfoForm.value.email;
    this.changeInfoService.update(user).subscribe(res => {
      this.showSuccess('Cập nhật thành công');
      this.router.navigate(['/change-info']);
    }, (err) => {
      this.showError('Cập nhật không thành công!');
      // console.log(err);
    });
  }


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
    if (this.changeInfoForm.invalid) {
      return;
    }
    else {
      this.saveUser(this.user);
      this.loadImage();
    }
  }

  loadImage() {
    let uploadDataAvatar = new FormData();
    uploadDataAvatar.set('file', this.selectFileAvatar);

    forkJoin(
      this.changeInfoService.upFileAvatar(uploadDataAvatar)

    )
      .subscribe(([res]) => {
        this.user.avatar = res;
        this.checkCompleteElement();
      });
  }


  checkCompleteElement() {
    if (this.user.avatar != null) {
      this.saveUser(this.user);
      return;
    } else {
      this.showError('Chưa upload avatar ');

    }


  }

  showPreviewAvatar(event: any) {
    // this.onUploadAvatar(event);
    if (event.target.files && event.target.files[0]) {
      this.selectFileAvatar = event.target.files[0];
      // this.addUserForm.get('fileAvatar').setValue(this.selectFileAvatar);
      var reader = new FileReader();
      reader.onload = (event: any) => {
        this.localUrlAvatar = event.target.result;
      }
      reader.readAsDataURL(event.target.files[0]);
      console.log("avatarrr", event.target.files[0]);
    }

  }
}