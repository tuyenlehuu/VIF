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
  templateUrl: 'change.info.component.html',
  styleUrls: ['change.info.component.scss']
})
export class ChangeInfoComponent {
  changeInfoForm: FormGroup;
  constructor(private toastrService: ToastrService, private fb: FormBuilder, private userService: UserService, private route: ActivatedRoute) {
    
  }
}