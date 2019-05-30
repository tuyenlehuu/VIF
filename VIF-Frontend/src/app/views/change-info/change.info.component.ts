import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ChangeInfoService } from '../../services/ChangeInfo.service';
import { User } from '../../models/ChangInfo.model';
import { forkJoin } from 'rxjs';

@Component({
  templateUrl: 'change.info.component.html',
  styleUrls: ['change.info.component.scss']
})
export class ChangeInfoComponent {
  changeInfoForm: FormGroup;
  changeEmail: FormGroup;
  submitted = false;
  user: User;
  localUrlAvatar: any[];
  selectFileAvatar: File;
  constructor(private toastrService: ToastrService, private fb: FormBuilder, private router: Router, private changeInfoService: ChangeInfoService, private route: ActivatedRoute) {
    this.creatForm();
  }
  creatForm() {
    this.changeInfoForm = this.fb.group({
      fileAvatar: ['', Validators.required],
      Email: ['', Validators.required],
    });
  }
  saveUser(user: User) {
    this.changeInfoService.update(user).subscribe(res => {
      this.showSuccess('Cập nhật thành công');
      this.router.navigate(['/change-info']);
    }, (err) => {
      this.showError('Cập nhật không thành công!');
      // console.log(err);
    });
  }

  loadImage() {
    let uploadAvatar = new FormData();
    uploadAvatar.set('file', this.changeInfoForm.get('fileAvatar').value);
    forkJoin(
      this.changeInfoService.upFileAvatar(uploadAvatar),

    )
      .subscribe((res) => {
        this.user.identityDocAvatar = res;
        this.checkCompleteElement();
      });
  }
  checkCompleteElement() {
    if (this.user.identityDocAvatar != null) {
      this.saveUser(this.user);
      return;
  }
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
  showPreviewAvatar(event: any) {
    if (event.target.files && event.target.files[0]) {
        var reader = new FileReader();
        reader.onload = (event: any) => {
            this.localUrlAvatar = event.target.result;
        }
        reader.readAsDataURL(event.target.files[0]);
    }
}
}