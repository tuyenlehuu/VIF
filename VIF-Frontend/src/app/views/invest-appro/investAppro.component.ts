import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';
import { Customer } from '../../models/Customer.model';
import { FormBuilder } from '@angular/forms';
import { Pager } from '../../models/Pager';
import { InvestAppro } from '../../models/InvestAppro.model'
import { InvestApproService } from '../../services/investAppro.service';
import { formatDate } from '../../helpers/function.share';
import { CustomerService } from '../../services/customer.service';
import { BsDatepickerConfig } from 'ngx-bootstrap';
import { InvestRequest } from '../../models/InvestRequest.model';
import { InvestRequestService } from '../../services/invest.request.service';

@Component({
  templateUrl: 'investAppro.component.html',

})
export class InvestApproComponent implements OnInit {
  investAppros: InvestAppro[] = [];
  customers: Customer[] = [];
  customerId: number;
  colorTheme = "theme-blue";
  responseObject: ResponseObject;
  p: number = 1;
  total: number;
  bsConfig: Partial<BsDatepickerConfig>;
  pageSize: number = 5;
  investApproSearch: InvestAppro = new InvestAppro();
  fromDate: Date;
  toDate: Date;
  modalRef: BsModalRef;
  investRequest: InvestRequest;

  typeOfRequest = [
    {
      name: 'Chọn Loại',
      value: 0
    },
    {
      name: 'Mua',
      value: 1
    },
    {
      name: 'Bán',
      value: 2
    }
  ];

  typeOfInvest = [
    {
      name: 'Chọn kiểu đầu tư',
      value: 0
    },
    {
      name: 'CCQ',
      value: 1
    },
    {
      name: 'CCQ đảm bảo',
      value: 2
    }
  ];

  status = [
    {
      name: 'Chọn trạng thái',
      value: 0
    },
    {
      name: 'Chờ phê duyệt',
      value: 1
    },
    {
      name: 'Chấp thuận',
      value: 2
    },
    {
      name: 'Từ chối',
      value: 3
    }
  ];

  constructor(private investReqService: InvestRequestService, private investApproService: InvestApproService, private customerService: CustomerService, private modalService: BsModalService, private toastrService: ToastrService, ) {
  }

  ngOnInit(): void {
    this.investApproService.getAll().pipe(first()).subscribe((res: any) => {
      this.investAppros = res;
    });
    this.search();
  }
  search() {
    this.getPage(1);
  }
  getPage(page: number) {
    var pager: Pager = new Pager();
    pager.page = page;
    pager.pageSize = this.pageSize;
    this.investApproService.getInvestApproByCondition(this.investApproSearch, formatDate(this.fromDate), formatDate(this.toDate), pager).pipe(first()).subscribe((respons: any) => {
      this.investAppros = respons.data;
      this.total = respons.totalRow;
      this.p = page;
      //console.log("data: ", respons);
    });
  }
  confirmDel(template: TemplateRef<any>, id: string) {
    this.modalRef = this.modalService.show(template);
    this.modalRef.content = id;
    this.investReqService.getById(Number(id)).subscribe((res: any)=>{
    this.investRequest = res;
    }
    )
    
  }
  reject() {
    this.investApproService.reject(this.modalRef.content).subscribe(res => {
      this.showSuccess('Từ chối thành công');
      this.getPage(1);
    });
    this.modalRef.hide();

  }
  accept() {
    this.investApproService.accept(this.investRequest).subscribe((res:any) => { 
      console.log("dataaaaaaaa:",res);
      this.showSuccess('Chấp thuận đầu tư thành công');
      this.getPage(1);
    });
    this.modalRef.hide();

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
