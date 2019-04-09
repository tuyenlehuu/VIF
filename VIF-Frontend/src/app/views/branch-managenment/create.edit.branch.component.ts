import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BranchService } from '../../services/branch.service';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Branch } from '../../models/Branch.model';
import { MustMatch, RequireCombo } from '../../helpers/function.share';
import { first } from 'rxjs/operators';
import { config } from '../../config/application.config';
import { TranslateService } from '@ngx-translate/core';
import { pipe } from 'rxjs';
@Component({
    templateUrl: 'create.edit.branch.component.html'
})
export class CEBranchComponent implements OnInit {
    id: number;
    isAddNew: boolean = true;
    branch: Branch;
    addBranchForm: FormGroup;
    editBranchForm: FormGroup;
    submitted = false; 

    constructor(private route: ActivatedRoute, private router: Router,
        private toastrService: ToastrService, private fb: FormBuilder,
        private branchService: BranchService, private translateService: TranslateService) {
        this.createForm();
        
    }

    ngOnInit(): void {

        this.id = this.route.snapshot.params['id'];
        if (this.id > 0) {
            // this.isAddNew = false;
            //goi api getBranchById(this.id);
            this.branchService.getBranchById(this.id).subscribe((res: Branch) => {
                this.branch = res;
                this.createEditForm();
                // console.log("lay duoc branch qua id", this.branch);
            });
        } else {
            this.branch = new Branch();
        }
    }
    status = [
        
        {
                name: 'Hoạt động',
                value: 1
        },
        {
            name: 'Ngừng hoạt động',
            value: 0
        }
    ];
    //    ngOnInit(): void {
    //         this.id = this.route.snapshot.params['id'];
    //         if (this.id > 0) {
    //             this.isAddNew = false;
    //             //goi api getBranchById(this.id);
    //             this.branchService.getBranchById(this.id).pipe(first()).subscribe((res:any) =>{
    //                 this.branch = res;
    //                 if(this.branch){
    //                     this.createEditForm();
    //                 }
    //                 // console.log("lay duoc branch qua id", this.branch);
    //             });
    //         } else {
    //             console.log("man hinh them moi", this.id);
    //         }
    //     }


    get addForm() { return this.addBranchForm.controls; }

    get editForm() { return this.editBranchForm.controls; }

    createForm() {
        this.addBranchForm = this.fb.group({
            aBranchCodeControl: ['', Validators.required],
            aBranchNameControl: ['', Validators.required],
            aActiveFlgControl: [1, Validators.required]
        });
    }

    createEditForm() {
        this.editBranchForm = this.fb.group({

            eBranchCodeControl: [{value:this.branch.branchCode,disabled:true},Validators.required],
            eBranchNameControl: [this.branch!=null?this.branch.branchName:'', Validators.required],
            eActiveFlgControl: [this.branch!=null?this.branch.activeFlg:'', Validators.required]
        });
        
    }

    onEditSubmit() {
        if (this.editBranchForm.invalid) {
            return;
        }
        
        this.branch.branchName=this.editBranchForm.value.eBranchNameControl;
        this.branch.activeFlg=this.editBranchForm.value.eActiveFlgControl;
        this.saveBranch(this.branch);
    }

    onAddSubmit(){
        this.submitted=true;
        if (this.addBranchForm.invalid) {
            return;
        }
        this.branch.branchCode=this.addBranchForm.value.aBranchCodeControl;
        this.branch.branchName=this.addBranchForm.value.aBranchNameControl;
        this.branch.activeFlg=this.addBranchForm.value.aActiveFlgControl;
        this.saveBranch(this.branch);
    }
    saveBranch(branch:Branch){
        if(branch.id>0){
            this.branchService.update(branch).subscribe((res:any) =>{
                // console.log("new branch:",res);
                this.translateService.get('vif.message.update_success').subscribe((res: string) => {
                    this.showSuccess(res);
                });
                this.router.navigate(['/branch-managenment']);
            },(err) =>{
                this.showError('Cập nhật không thành công!');
                // console.log(err);
            });
        }else{
            this.branchService.register(branch).pipe(first()).subscribe((respones:any) => {
                if(respones.code === 409){
                    this.translateService.get('vif.branch.exit').subscribe((rep:string)=>{
                        this.showError(rep);
                    });
                }else if(respones.code === 200){
                    this.translateService.get('vif.message.create_success').subscribe((rep:string)=>{
                        this.showSuccess(rep);
                    });
                    this.router.navigate(['/branch-managenment']);
                }
                (err)=>{
                    this.translateService.get('vif.message.create_failed').subscribe((rep:string)=>{
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
