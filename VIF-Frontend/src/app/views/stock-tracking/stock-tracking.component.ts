import { Component, OnInit, TemplateRef } from '@angular/core';
import { StockTracking } from '../../models/StockTracking.model';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { StockTrackingService } from '../../services/stockTracking.service';
import { first } from 'rxjs/operators';
import { Pager } from '../../models/Pager';

@Component({
  selector: 'app-other.asset',
  templateUrl: './stock-tracking.component.html',
  styleUrls: ['./stock-tracking.component.scss']
})
export class StockTrackingComponent implements OnInit {
  stockCode: string;
  stockTrackingList: StockTracking [] = [];
  modalRef: BsModalRef;
  p: number = 1;
  total: number;
  pageSize: number = 10;
  stockCondition: StockTracking;
  classRow: string;
  
  constructor(private modalService: BsModalService, private stockTrackingService: StockTrackingService, private toastrService: ToastrService) {
    // this.stockTrackingService.getAll().pipe(first()).subscribe((res: any) => {
    //   if(res){
    //     this.stockTrackingList = res.data;
    //   }
    //   // console.log("this.groupAssets",this.groupAssets);
    // });
  }

  ngOnInit() {
    this.getPage(1);
  }

  getPage(page: number) {
    var pager: Pager = new Pager();
    pager.page = page;
    pager.pageSize = this.pageSize;
    this.stockCondition = new StockTracking();
    this.stockCondition.code = this.stockCode;
    
    this.stockTrackingService.getStockTrackingsByCondition(this.stockCondition, pager).pipe(first()).subscribe((respons: any) => {
      this.stockTrackingList = respons.data;
      this.total = respons.totalRow;
      this.p = page;
    });
  }

  search(){
    this.getPage(1);
  }

  confirmDel(template: TemplateRef<any>, stockId: string) {
    this.modalRef = this.modalService.show(template);
    this.modalRef.content = stockId;
  }

  delStock(){
    this.stockTrackingService.deleteStockTrackingById(this.modalRef.content).subscribe(res => {
      this.showSuccess('Xóa thành công');
      this.getPage(1);
    }, catchError => {
      // console.log("result: ", catchError);
      this.showError("Có lỗi trong quá trình xóa. Vui lòng liên hệ admin!");
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