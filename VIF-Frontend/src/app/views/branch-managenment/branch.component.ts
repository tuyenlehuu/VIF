import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { Pager } from '../../models/Pager';
import { Branch } from '../../models/Branch.model';
import { BranchService } from '../../services/branch.service';
import { ResponseObject } from '../../models/Response.model';


@Component({
    templateUrl: 'branch.component.html',
    styleUrls: ['branch.component.css']
})
export class BranchComponent implements OnInit {
    branchs:Branch[]=[];
    total:number;
    branchSearch: Branch=new Branch();
    pageSize: number = 5;
    p:number=1;
    status = [
        {
            name: 'Chọn trạng thái',
            value: -1
        },
        {
                name: 'Hoạt động',
                value: 1
        },
        {
            name: 'Ngừng hoạt động',
            value: 0
        }
    ];

    constructor( private toastrService: ToastrService, private branchService:BranchService) {  
    }
   

    ngOnInit(): void {
       this.branchService.getAll().pipe(first()).subscribe((respons:any) =>{
           this.branchs=respons;
           console.log("this.branchs: ", this.branchs);
       });
    }
    // getPage(page: number) {
    //     var pager: Pager = new Pager();
    //     pager.page = page;
    //     pager.pageSize = this.pageSize;
    //     this.branchService.getBranchByCondition(this.branchSearch, pager).pipe(first()).subscribe((respons: any) => {
    //         this.branchs = respons.data;
    //         this.total = respons.totalRow;
    //         this.p = page;
    //         // console.log("data: ", respons);
    //     });
    // }


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

    changeScreen(typeScreen: number) {
        
    }

    search() {
        // console.log("userSearch: ", this.userSearch);
        // this.getPage(1);
    }
    
}
