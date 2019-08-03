import { Component, OnInit, TemplateRef } from '@angular/core';
import { AppParam } from '../../models/AppParam.model';
import { AppParamService } from '../../services/appParam.service';
import { first } from 'rxjs/operators';
import { config } from '../../config/application.config';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { Pager } from '../../models/Pager';

@Component({
    templateUrl: 'appParam.component.html'
})
export class AppParamComponent implements OnInit {
    appParams: AppParam[] = [];
    appParamSearch: AppParam = new AppParam();
    modalRef: BsModalRef;
    p: number = 1;
    total: number;
    pageSize: number = 5;
    propKey: string;
    propValue: string;
    description: string;
    propType: string;

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
            value: 0 && null
        }
    ];

    constructor(private appParamService: AppParamService, private modalService: BsModalService, private toastrService: ToastrService, ) {
    }

    ngOnInit(): void {
        this.appParamService.getAll().pipe(first()).subscribe((res: any) => {
            this.appParams = res.data;
        });
        this.getPage(1);
    }

    getPage(page: number) {
        var pager: Pager = new Pager();
        pager.page = page;
        pager.pageSize = this.pageSize;
        this.appParamService.getAppParamsByCondition(this.appParamSearch, pager).pipe(first()).subscribe((respons: any) => {
            this.appParams = respons.data;
            this.total = respons.totalRow;
            this.p = page;
            //console.log("data: ", respons);
        });
    }

    confirmDel(template: TemplateRef<any>, id: string) {
        this.modalRef = this.modalService.show(template);
        this.modalRef.content = id;
    }

    deleteAppParam() {
        this.appParamService.deleteById(this.modalRef.content).subscribe(res => {
            this.showSuccess('Xóa thành công');
            this.getPage(1);
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
    }

    enterOnSubmitPropKey() {
        console.log(this.propKey);
        this.getPage(1);
    }
	
    enterOnSubmitPropType(){
        this.getPage(1);
    }
    enterOnSubmitDescription() {
        console.log(this.description);
    }

    enterOnSubmitPropValue(){
        
    }
}