import { Component, OnInit, TemplateRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BsDatepickerConfig } from 'ngx-bootstrap';
import { Branch } from '../../models/Branch.model';
import { MustMatch, RequireCombo } from '../../helpers/function.share';
import { first } from 'rxjs/operators';
import { config } from '../../config/application.config';
import { TranslateService } from '@ngx-translate/core';
import { pipe } from 'rxjs';
import { HolderEquity } from '../../models/HolderEquity';
import { HolderequityService } from '../../services/HolderEquity.service';
import { HolderHistory } from '../../models/holderHistory.model';

@Component({
    templateUrl: 'create.edit.holderequity.component.html',
})
export class CEHolderequityComponent implements OnInit {
    id: number;
    idNumber: number;
    isAddNew: boolean = true;
    stringDate: string;
    holderHistory: HolderHistory = new HolderHistory();
    holder: HolderEquity;
    addHolderForm: FormGroup;
    editHolderForm: FormGroup;
    colorTheme = 'theme-blue';
    bsConfig: Partial<BsDatepickerConfig>;
    submitted = false;

    constructor(private route: ActivatedRoute, private router: Router,
        private toastrService: ToastrService, private fb: FormBuilder,
        private holderService: HolderequityService, private translateService: TranslateService) {
        this.createForm();
        this.bsConfig = Object.assign({}, { containerClass: this.colorTheme });

    }

    ngOnInit(): void {
        this.id = this.route.snapshot.params['id'];
        if (this.id > 0) {
            // this.isAddNew = false;
            //goi api getBranchById(this.id);
            this.holderService.getById(this.id).subscribe((res: HolderEquity) => {
                this.holder = res;
                this.createEditForm();
                // console.log("lay duoc branch qua id", this.branch);
            });
        } else {
            this.holder = new HolderEquity();
        }

    }

    get addForm() { return this.addHolderForm.controls; }
    get editForm() { return this.editHolderForm.controls; }
    getDateToString(dateToString: string): void {

        this.stringDate = dateToString;
    }

    createForm() {
        this.addHolderForm = this.fb.group({
            aFullName: ['', Validators.required],
            aEmail: ['', Validators.required],
            aTel: ['', Validators.required],
            aTitle: ['', Validators.required],
            aAddress: ['', Validators.required],
            aAmount: [0, Validators.required],
            aPrice: [0, Validators.required],
            aLastUpdate: ['', Validators.required],
            aDescription: ['', Validators.required]
        });
    }

    createEditForm() {
        this.editHolderForm = this.fb.group({
            eFullName: [{ value: this.holder.fullName, disabled: true }, Validators.required],
            eEmail: [{ value: this.holder.email, disabled: false }, Validators.required],
            eTel: [{ value: this.holder.tel, disabled: false }, Validators.required],
            eTitle: [{ value: this.holder.title, disabled: false }, Validators.required],
            eAddress: [{ value: this.holder.address, disabled: false }, Validators.required],
            eAmount: [{ value: this.holder.amount, disabled: false }, Validators.required],
            ePrice: [{ value: this.holder.price, disabled: false }, Validators.required],
            eLastUpdate: [{ value: this.holder.lastUpdate, disabled: false }, Validators.required],
            eDescription: [{ value: this.holder.description, disabled: false }, Validators.required],
            eReason: ['', Validators.required]
        });
        var date: Date = new Date(this.holder.lastUpdate);
        this.getDateToString(date.toDateString());


    }
    onAddSubmit() {
        if (this.addHolderForm.invalid) {
            this.showError('Chưa hoàn thành thông tin!');
            return;
        }
        this.submitted = true;

        this.holder.fullName = this.addHolderForm.value.aFullName;
        this.holder.email = this.addHolderForm.value.aEmail;
        this.holder.tel = this.addHolderForm.value.aTel;
        this.holder.title = this.addHolderForm.value.aTitle;
        this.holder.address = this.addHolderForm.value.aAddress;
        this.holder.amount = this.addHolderForm.value.aAmount;
        this.holder.price = this.addHolderForm.value.aPrice;
        this.holder.lastUpdate = this.addHolderForm.value.aLastUpdate;
        this.holder.description = this.addHolderForm.value.aDescription;

        console.log(this.holder);
        this.saveHolderequity(this.holder);
    }
    onEditSubmit() {

        if (this.editHolderForm.invalid) {
            this.showError('Chưa hoàn thành thông tin!');
            return;
        }

        this.holder.email = this.editHolderForm.value.eEmail;
        this.holder.tel = this.editHolderForm.value.eTel;
        this.holder.title = this.editHolderForm.value.eTitle;
        this.holder.address = this.editHolderForm.value.eAddress;
        this.holder.amount = this.editHolderForm.value.eAmount;
        this.holder.price = this.editHolderForm.value.ePrice;
        this.holder.lastUpdate = this.editHolderForm.value.eLastUpdate;
        this.holder.description = this.editHolderForm.value.eDescription;
        this.holderHistory.reason = this.editHolderForm.value.eReason;
        this.holderHistory.amount = this.holder.amount;
        this.holderHistory.price = this.holder.price;
        this.holderHistory.description = this.holder.description;
        this.holderHistory.lastUpdate = this.holder.lastUpdate;
        this.idNumber = this.route.snapshot.params['id'];
        this.holderHistory.holder.id = this.idNumber;
        this.holderHistory.holder = this.holder;
        this.saveHolderequity(this.holder);
        this.holderService.add(this.holderHistory, this.holderHistory.holder.id).pipe(first()).subscribe((respones: any) => {

        });
    

    }
    saveHolderequity(holder: HolderEquity) {
        if (holder.id > 0) {
            this.holderService.update(holder).subscribe((res: any) => {

                this.translateService.get('vif.message.update_success').subscribe((res: string) => {
                    this.showSuccess(res);
                });

                this.router.navigate(['/holder-equity']);
            }, (err) => {
                this.showError('Cập nhật không thành công!');
                // console.log(err);
            });
        } else {
            this.holderService.register(holder).pipe(first()).subscribe((respones: any) => {

                if (respones.code === 409) {
                    this.translateService.get('vif.branch.exit').subscribe((rep: string) => {
                        this.showError(rep);
                    });
                } else if (respones.code === 200) {
                    this.translateService.get('vif.message.create_success').subscribe((rep: string) => {
                        this.showSuccess(rep);
                    });
                    this.router.navigate(['/holder-equity']);
                }
                (err) => {
                    this.translateService.get('vif.message.create_failed').subscribe((rep: string) => {
                        this.showError(rep);
                    });
                }
            });
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

}
