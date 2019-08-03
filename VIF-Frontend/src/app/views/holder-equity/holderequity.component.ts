import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { Pager } from '../../models/Pager';
import { ResponseObject } from '../../models/Response.model';
import { BsDatepickerConfig } from 'ngx-bootstrap';
import { HolderequityService } from '../../services/HolderEquity.service';
import { formatDate } from '../../helpers/function.share';
import { HolderEquity } from '../../models/HolderEquity';
import { toDate } from '@angular/common/src/i18n/format_date';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

@Component({
    templateUrl: 'holderequity.component.html',
    styleUrls: ['holderequity.component.css']
})
export class HolderequityComponent implements OnInit {
   
    holderSearch:HolderEquity=new HolderEquity();
    responseObject: ResponseObject;
    date: any;
    bsConfig: Partial<BsDatepickerConfig>;
    colorTheme = "theme-blue";
    fromDate: Date;
    toDate: Date;
    p: number = 1;
    total: number;
    pageSize: number = 5;
    holderequity: HolderEquity[]=[];
    modalRef: BsModalRef;

    ngOnInit(): void {
        this.holderequityService.getAll().pipe(first()).subscribe((res: any) => {
            this.holderequity = res.data;
        });
    }

    constructor(private toastrService: ToastrService,private modalService:BsModalService, private holderequityService: HolderequityService) {
        this.bsConfig = Object.assign({}, { containerClass: this.colorTheme });

    }
    getPage(page: number) {
        var pager: Pager = new Pager();
        pager.page = page;
        pager.pageSize = this.pageSize;
        this.holderequityService.getHolderequityByCondition(this.holderSearch,formatDate(this.fromDate),formatDate(this.toDate), pager).pipe(first()).subscribe((respons: any) => {
            this.holderequity = respons.data;
            this.total = respons.totalRow;
            this.p = page;
            console.log(this.holderequity);

        })
    }

    search(){
        if(!this.fromDate && !this.toDate){
            this.getPage(1);
          }
           else if(!this.fromDate){
             this.showError("Mời bạn nhập ngày bắt đầu");
           }else if(!this.toDate){
             this.showError("Mời bạn nhập ngày tới");
           }else if(this.fromDate && this.toDate){
             var time=(this.toDate.getTime()-this.fromDate.getTime())/1000;
             if(time<0){
               this.showError("Ngày kết thúc nhỏ hơn ngày tới, vui lòng nhập lại!!!");
             }else{
               this.getPage(1);
             }
           }

    }
    onSubmit(){
        if(this.holderSearch.fullName){
            this.getPage(1);
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
    confirmDel(template: TemplateRef<any>, branchID: string) {
        this.modalRef = this.modalService.show(template);
        this.modalRef.content = branchID;
    }
    deleteHolder(){

        this.holderequityService.deleteById(this.modalRef.content).subscribe(res=>{
            this.showSuccess("Xoá thành công");
            this.holderequityService.getAll().pipe(first()).subscribe((res: any) => {
                this.holderequity = res.data;
                console.log(this.holderequity);
            });
        },catchError=>{
            console.log("result: ",catchError);
        });
        this.modalRef.hide();
    }

    enterOnSubmitDate(e){

    }
}
