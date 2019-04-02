import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BranchService } from '../../services/branch.service';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Branch } from '../../models/Branch.model';
import { MustMatch, RequireCombo } from '../../helpers/function.share';
import { first } from 'rxjs/operators';
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
        private branchService: BranchService) {
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
            console.log("man hinh them moi", this.id);
        }
    }
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
            aActiveFlgControl: ['', Validators.required]
        });
    }

    createEditForm() {
        this.editBranchForm = this.fb.group({
            eBranchCodeControl: [this.branch!=null?this.branch.branchCode:'', Validators.required],
            eBranchNameControl: [this.branch!=null?this.branch.branchName:'', Validators.required]
        });
    }

    onEditSubmit() {
        if (this.editBranchForm.invalid) {
            return;
        }
        console.log("goi ham sua");
    }

    onAddSubmit(){

    }
}
