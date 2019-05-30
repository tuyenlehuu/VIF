import { Component, OnInit, TemplateRef } from '@angular/core';
import { BillingInfo } from '../../models/BillingInfo.model';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';
import { BillingInfoService } from '../../services/billing.service';
import { ToastrService } from 'ngx-toastr';
import { Pager } from '../../models/Pager';
import { first } from 'rxjs/operators';
import { config } from '../../config/application.config';

@Component({
  selector: 'billing-info',
  templateUrl: './billing-info.component.html'
})
export class BillingInfoComponent implements OnInit {
 

    bInfos: BillingInfo[] = [];
    modalRef: BsModalRef;
    bInfoSearch: BillingInfo = new BillingInfo();

    p: number = 1;
    total: number;
    pageSize: number = 5;
   

    constructor(private bInfoService: BillingInfoService, private modalService: BsModalService, private toastrService: ToastrService) { }

    ngOnInit(): void {
  
        this.getPage(1);
    }

    getPage(page: number) {
        var pager: Pager = new Pager();
        pager.page = page;
        pager.pageSize = this.pageSize;
        this.bInfoService.getUsersByCondition(this.bInfoSearch, pager).pipe(first()).subscribe((respons: any) => {
            this.bInfos = respons.data;
            this.total = respons.totalRow;
            this.p = page;
        });
    }

    confirmDel(template: TemplateRef<any>, bInfoId: string) {
        this.modalRef = this.modalService.show(template);
        this.modalRef.content = bInfoId;
    }

    delete() {

        this.bInfoService.deleteById(this.modalRef.content).subscribe(res => {
            this.showSuccess('Xóa thành công');
            // this.userService.getAll().pipe(first()).subscribe((respons: any) => {
            //     this.users = respons.data;
            // });
            this.getPage(1);
        }, catchError => {
            // console.log("result: ", catchError);
        });
        this.modalRef.hide();
    }

    showSuccess(mes: string) {
        this.toastrService.success('', mes, {
            timeOut: config.timeoutToast
        });
    }

    search() {
        this.getPage(1);
        console.log("banhk=>>>>",this.bInfoSearch.bankName);
    }
}