import { Component, OnInit } from '@angular/core';
import { AppParam } from '../../models/AppParam.model';
import { AppParamService } from '../../services/appParam.service';
import { first } from 'rxjs/operators';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MustMatch, RequireCombo } from '../../helpers/function.share';

@Component({
  templateUrl: 'create.edit.appParam.component.html'
})
export class CEAppParamComponent implements OnInit {
  addAppParamForm: FormGroup;
  id: number;
  submitted = false;
  appParam: AppParam;
  editAppParamForm: FormGroup;
  status = [
    {
      name: 'Hoạt động',
      value: 1
    },
    {
      name: 'Ngừng hoạt động',
      value: 0 && null
    }
  ];

  propType = [
    {
      name: 'Phí giao dịch CCQ',
      value: '1'
    },
    {
      name: 'Phí giao dịch chứng khoán',
      value: '2'
    }
  ];

  constructor(private route: ActivatedRoute, private appParamService: AppParamService, private router: Router, private toastrService: ToastrService, private fb: FormBuilder) {
    this.createForm();
  }
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    if (this.id > 0) {
      this.appParamService.getById(this.id).subscribe((res: AppParam) => {
        this.appParam = res;
        // console.log("current user: ", this.user);
        this.createEditForm();
      })
    } else {
      this.appParam = new AppParam();
    }
  }
  createForm() {
    this.addAppParamForm = this.fb.group({
      propKey: ['', Validators.required],
      propType: ['1', Validators.required],
      propValue: ['', Validators.required],
      status: [1, Validators.required],
      description: []
    });
  }

  createEditForm() {
    this.editAppParamForm = this.fb.group({
      ePropKey: [this.appParam.propKey, Validators.required],
      ePropType: [this.appParam.propType, Validators.required],
      ePropValue: [this.appParam.propValue, Validators.required],
      eStatus: [this.appParam.activeFlg, Validators.required],
      eDescription: [this.appParam.description, Validators.required]
    });
  }
  get addForm() { return this.addAppParamForm.controls; }
  get editForm() { return this.editAppParamForm.controls; }

  saveAppParam(appParam: AppParam) {
    if (this.id > 0) {
      this.appParamService.update(appParam).pipe(first()).subscribe((respons: any) => {
        console.log("res", respons);
        if(respons.code === 409){
          this.showError('Config đã tồn tại');
        }else{
        this.showSuccess('Cập nhật thành công');}
        this.router.navigate(['/app-param']);
      }, (err) => {
        this.showError('Cập nhật config không thành công!');
        console.log(err);
      });
    } else {
      this.appParamService.register(appParam).pipe(first()).subscribe((respons: any) => {
        console.log("res", respons);
        if(respons.code === 409){
          this.showError('Config đã tồn tại');
        }else{
        this.showSuccess('Thêm mới thành công');}
        this.router.navigate(['/app-param']);
      }, (err) => {
        this.showError('Thêm mới config không thành công!');
        console.log(err);
      });
    }
  }
  onAddSubmit() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.addAppParamForm.invalid) {
      return;
    }
    this.appParam.propKey = this.addAppParamForm.value.propKey;
    this.appParam.propType = this.addAppParamForm.value.propType;
    this.appParam.propValue = this.addAppParamForm.value.propValue;
    this.appParam.activeFlg = this.addAppParamForm.value.status;
    this.appParam.description = this.addAppParamForm.value.description;
    this.saveAppParam(this.appParam);
  }

  onEditSubmit() {
    if (this.editAppParamForm.invalid) {
      return;
    }

    this.appParam.propKey = this.editAppParamForm.value.ePropKey;
    this.appParam.propType = this.editAppParamForm.value.ePropType;
    this.appParam.propValue = this.editAppParamForm.value.ePropValue;
    this.appParam.activeFlg = this.editAppParamForm.value.eStatus;
    this.appParam.description = this.editAppParamForm.value.eDescription;
    this.saveAppParam(this.appParam);
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
}