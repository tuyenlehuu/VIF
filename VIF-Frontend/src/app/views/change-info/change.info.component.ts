import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ChangeInfoService } from '../../services/ChangeInfo.service';
import { User } from '../../models/ChangInfo.model';

@Component({
  templateUrl: 'change.info.component.html',
  styleUrls: ['change.info.component.scss']
})
export class ChangeInfoComponent {
  changeInfoForm: FormGroup;
  submitted = false;
  user: User;
  localUrlAvatar: any[];
  selectFileAvatar: File;
  constructor(private toastrService: ToastrService, private fb: FormBuilder, private changeInfoService: ChangeInfoService, private route: ActivatedRoute) {
    this.creatForm();
  }
  creatForm() {
    this.changeInfoForm = this.fb.group({
      fileAvatar: [this.user.avatar, Validators.required],
      Email: [this.user.email, Validators.required],
    });
  }
}