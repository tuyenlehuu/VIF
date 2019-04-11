import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { Pager } from '../../models/Pager';
import { Branch } from '../../models/Branch.model';
import { BranchService } from '../../services/branch.service';
import { ResponseObject } from '../../models/Response.model';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';



@Component({
    templateUrl: 'branch.component.html',
    styleUrls: ['branch.component.css']
})
export class BranchComponent implements OnInit {
    branchs: Branch[] = [];
    total: number;
    branchSearch: Branch = new Branch();
    pageSize: number = 5;
    p: number = 1;
    modalRef: BsModalRef;
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

    constructor(private toastrService: ToastrService, private modalService: BsModalService, private branchService: BranchService) {
    }

    ngOnInit(): void {
        this.getPage(1);
    }
    getPage(page: number) {
        var pager: Pager = new Pager();
        pager.page = page;
        pager.pageSize = this.pageSize;
        this.branchService.getUsersByCondition(this.branchSearch, pager).pipe(first()).subscribe((respons: any) => {
            this.branchs = respons.data;
            this.total = respons.totalRow;
            this.p = page;

        })
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
    confirmDel(template: TemplateRef<any>, branchID: string) {
        this.modalRef = this.modalService.show(template);
        this.modalRef.content = branchID;
    }
    deleteBranch() {
        // console.log("Start delete: ", this.modalRef.content);
        this.branchService.deleteById(this.modalRef.content).subscribe(res => {
            this.showSuccess('Xóa thành công');

            this.branchService.getAll().pipe(first()).subscribe((respons: any) => {
                this.branchs = respons;

            });

        }, catchError => {
            console.log("result: ", catchError);
        });
        this.modalRef.hide();
    }

    changeScreen(typeScreen: number) {

    }

    search() {
        this.getPage(1);

    }

}
