import { Component, OnInit, TemplateRef } from '@angular/core';
import { StockTracking } from '../../models/StockTracking.model';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { StockTrackingService } from '../../services/stockTracking.service';
import { first } from 'rxjs/operators';
import { Pager } from '../../models/Pager';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ShareMasterService } from '../../services/sharemaster.service';
import { ShareMaster } from '../../models/ShareMaster.model';
import { NotEqualZero } from '../../helpers/function.share';

@Component({
  selector: 'app-other.asset',
  templateUrl: './ce-stock-tracking.component.html',
  styleUrls: ['./stock-tracking.component.scss']
})
export class CEStockTrackingComponent implements OnInit {
  stockCode: string;
  sub: any;
  id: any;
  stockTracking: StockTracking;
  addStockTrackingForm: FormGroup;
  submitted = false;
  editStockTrackingForm: FormGroup;
  shareMasterList: ShareMaster[] = [];
  newStockTracking: StockTracking = new StockTracking();
  nameStockTemp: string;

  constructor(private route: ActivatedRoute, private stockTrackingService: StockTrackingService, private router: Router,
    private toastrService: ToastrService, private fb: FormBuilder, private translateService: TranslateService,
    private shareMasterService: ShareMasterService) {
    this.shareMasterService.getAllShares().pipe(first()).subscribe((res: any) => {
      this.shareMasterList = res;
      this.createAddForm();
    });
  }

  ngOnInit() {
    // this.getPage(1);
    this.id = this.route.snapshot.params['id'];
    if (this.id > 0) {
      this.stockTrackingService.getStockTrackingById(this.id).subscribe((res: any) => {
        if(res){
          this.stockTracking = res;
          // console.log("mStock: ", this.stockTracking);
          if(this.stockTracking && this.stockTracking.code){
            this.createEditForm();
          }
        }
      })
    } else {
      this.stockTracking = new StockTracking();
    }
  }

  createAddForm() {
    this.addStockTrackingForm = this.fb.group({
      stockCode: [null, Validators.required],
      stockName: [{value: '', disabled: true}, Validators.required],
      targetBuy: [0, Validators.required],
      targetSell: [0, Validators.required],
      description: ['']
    },
    {
      validator: [NotEqualZero('targetBuy'), NotEqualZero('targetSell')]
    });
  }

  createEditForm() {
    this.editStockTrackingForm = this.fb.group({
      eStockCode: [{ value: this.stockTracking.code, disabled: true }, Validators.required],
      eDescription: [this.stockTracking.description],
      eTargetBuy: [this.stockTracking.targetPriceBuy, Validators.required],
      eTargetSell: [this.stockTracking.targetPriceSell, Validators.required]
    },
    {
      validator: [NotEqualZero('eTargetBuy'), NotEqualZero('eTargetSell')]
    });
  }

  onAddSubmit(){
    if (this.addStockTrackingForm.invalid) {
      return;
    }

    this.newStockTracking.status = 1;
    this.newStockTracking.targetPriceBuy = this.addStockTrackingForm.value.targetBuy != null ? this.addStockTrackingForm.value.targetBuy : 0;
    this.newStockTracking.targetPriceSell = this.addStockTrackingForm.value.targetSell != null ? this.addStockTrackingForm.value.targetSell : 0;
    this.newStockTracking.code = this.addStockTrackingForm.value.stockCode;
    this.newStockTracking.name = this.nameStockTemp != null?this.nameStockTemp:'';
    this.newStockTracking.description = this.addStockTrackingForm.value.description != null ? this.addStockTrackingForm.value.description : '';

    // console.log("mTracking: ", this.newStockTracking);
    this.stockTrackingService.addStockTracking(this.newStockTracking).pipe(first()).subscribe((respons: any) => {
      if (respons.code === 409) {
        this.translateService.get('vif.message.stock_exists').subscribe((res: string) => {
          this.showError(res);
        });
      } else if (respons.code === 200) {
        this.translateService.get('vif.message.create_success').subscribe((res: string) => {
          this.showSuccess(res);
        });
        this.router.navigate(['/stock-tracking-management']);
      }
    }, (err) => {
      this.translateService.get('vif.message.create_failed').subscribe((res: string) => {
        this.showError(res);
      });
    });
  }

  onEditSubmit(){
    if (this.editStockTrackingForm.invalid) {
      return;
    }

    // this.stockTracking.code = this.editStockTrackingForm.value.eStockCode;
    this.stockTracking.targetPriceBuy = this.editStockTrackingForm.value.eTargetBuy;
    this.stockTracking.targetPriceSell = this.editStockTrackingForm.value.eTargetSell;
    this.stockTracking.description =  this.editStockTrackingForm.value.eDescription;

    // console.log("Stock Tracking!", this.stockTracking);

    this.stockTrackingService.update(this.stockTracking).subscribe(res => {
      // console.log("new user: ", res);
      this.translateService.get('vif.message.update_success').subscribe((res: string) => {
        this.showSuccess(res);
      });
      this.router.navigate(['/stock-tracking-management']);
    }, (err) => {
      this.translateService.get('vif.message.update_failed').subscribe((res: string) => {
        this.showError(res);
      });
    });
  }

  onChangeStockCode(){
    if(this.addForm.stockCode){
      this.shareMasterService.getByShareByCode(this.addForm.stockCode.value).subscribe((res: any) => {
        if(res){
          // console.log("this.stock: ", res);
          // var shareMasterSelected: ShareMaster = res;
          this.addForm.stockName.setValue(res.cpName);
          this.nameStockTemp = res.cpName;
          // console.log("this.addStockTrackingForm.value.stockName: ", this.nameStockTemp);
          
        }
      });
    }
  }

  get addForm() { return this.addStockTrackingForm.controls; }

  get editForm() { return this.editStockTrackingForm.controls; }

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