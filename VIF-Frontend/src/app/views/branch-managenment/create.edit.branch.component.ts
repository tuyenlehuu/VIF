import { Component, OnInit, TemplateRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BranchService } from '../../services/branch.service';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Branch } from '../../models/Branch.model';
import { MustMatch, RequireCombo } from '../../helpers/function.share';
@Component({
    templateUrl:'create.edit.branch.component.html'
})
export class  CEBranchComponent implements OnInit {
   id:any
//    branch:Branch
//    addUserForm: FormGroup;
//    editUserForm: FormGroup;
    ngOnInit(): void {
        // this.id = this.route.snapshot.params['id'];
        // if (this.id > 0) {
        //     this.branchService.getById(this.id).subscribe((res: Branch) => {
        //         this.branch = res;
        //         // console.log("current user: ", this.user);
        //         this.createEditForm();
        //     })
        // } else {
        //     this.branch = new Branch();
        // }
        this.id = this.route.snapshot.params['id'];
        if (this.id > 0) {
            console.log("id nhan duoc", this.id);
        } else {
            console.log("id nay bang khong", this.id);
        }
    }
    constructor(private route: ActivatedRoute, private router: Router, private toastrService: ToastrService, private fb: FormBuilder){
        // this.createForm();
    }
    // createForm() {
    //     this.addUserForm = this.fb.group({
    //         username: ['', Validators.required],
    //         password: ['', [Validators.required, Validators.minLength(8)]],
    //         email: ['', Validators.required, Validators.email],
    //         confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
    //         role: ['ROLE_USER', Validators.required],
    //         status: [1, Validators.required]
    //     },{
    //         validator: [MustMatch('password', 'confirmPassword'), RequireCombo('role')]
    //     });
    // }
    // createEditForm(){
    //     this.editUserForm = this.fb.group({
    //         eUsername: [{value: this.user.username, disabled: true}, Validators.required],
    //         eEmail: [this.user.email, Validators.required],
    //         eRole: [this.user.role, Validators.required],
    //         eStatus: [this.user.activeFlg, Validators.required]
    //     });
    // }
}
