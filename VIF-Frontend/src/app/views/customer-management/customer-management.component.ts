import { Component, OnInit,  TemplateRef } from '@angular/core';
import { Customer } from '../../models/Customer.model';
import { CustomerService } from '../../services/customer.service';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { error } from '@angular/compiler/src/util';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';
import { Pager } from '../../models/Pager';

@Component({
  selector: 'app-customer-management',
  templateUrl: './customer-management.component.html',
  styleUrls: ['./customer-management.component.scss']
})
export class CustomerManagementComponent implements OnInit {

  customers: Customer[] = [];
    modalRef: BsModalRef;
    customerSearch: Customer = new Customer();

    p: number = 1;
    total: number;
    pageSize: number = 5;
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

    constructor(private customerService: CustomerService, private modalService: BsModalService, private toastrService: ToastrService) { }

    ngOnInit(): void {
        this.getPage(1);
    }

    getPage(page: number) {
        var pager: Pager = new Pager();
        pager.page = page;
        pager.pageSize = this.pageSize;
        this.customerService.getCustomersByCondition(this.customerSearch, pager).pipe(first()).subscribe((respons: any) => {
            this.customers = respons.data;
            this.total = respons.totalRow;
            this.p = page;
            // console.log("data: ", respons);
        });
    }

    confirmDel(template: TemplateRef<any>, customerId: string) {
        this.modalRef = this.modalService.show(template);
        this.modalRef.content = customerId;
    }

    deleteCustomer() {
         console.log("Start delete: ", this.modalRef.content);
        this.customerService.deleteCustomerById(this.modalRef.content).subscribe(res => {
            this.showSuccess('Xóa thành công');

            this.getPage(1);
        }, catchError => {
            console.log("result: ", catchError);
        });
        this.modalRef.hide();
    }

    showSuccess(mes: string) {
        this.toastrService.success('', mes, {
            timeOut: config.timeoutToast
        });
    }

    search() {
        // console.log("userSearch: ", this.userSearch);
        this.getPage(1);
    }


  
}
