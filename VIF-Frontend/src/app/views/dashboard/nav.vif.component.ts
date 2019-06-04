import { Component, OnInit, Input, AfterViewChecked } from '@angular/core';
import { DashboardService } from '../../services/dashboard.service';
import { first } from 'rxjs/operators';
import { NavModel } from '../../models/NavReport.model';
import { KeyNameValue } from '../../models/KeyNameValue.model';
import { Asset } from '../../models/Asset.model';
import { AssetService } from '../../services/asset.service';
import { CustomerService } from '../../services/customer.service';
import { Customer } from '../../models/Customer.model';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker';
import { formatDate } from '../../helpers/function.share';

@Component({
  selector: 'nav-screen',
  templateUrl: 'nav.vif.component.html'
})
export class NAVScreenComponent implements OnInit {
  navList: NavModel[] = [];
  isByMonth: boolean = false;
  navChartDataLst: KeyNameValue[] = [];
  asset: Asset;
  customerSelectedId: number;
  customers: Customer[] = [];
  toDate: Date;
  fromDate: Date;
  bsConfig: Partial<BsDatepickerConfig>;
  colorTheme = "theme-blue";
  model = { options: '0' };

  // Line NAV
  // lineChart
  public lineChartData: Array<any> = [
    { data: [], label: 'NAV' }
  ];
  public lineChartLabels: Array<any> = [];
  public lineChartOptions: any = {
    animation: false,
    responsive: true
  };
  public lineChartColours: Array<any> = [
    { // grey
      backgroundColor: '#fff',
      borderColor: 'rgb(105, 195, 255)',
      pointBackgroundColor: 'rgb(255, 139, 164)',
      pointBorderColor: 'rgb(255, 139, 164)',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)',
      fill: false
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  public lineChartLegend = true;
  public lineChartType = 'line';


  constructor(private dashboardService: DashboardService, private assetService: AssetService, private customerService: CustomerService) {
    this.bsConfig = Object.assign({}, { containerClass: this.colorTheme });
    this.customerService.getAll().pipe(first()).subscribe((respons: any) => {
      // console.log("data: ", respons);
      this.customers = respons;
    });
    this.search();
  }

  ngOnInit(): void {
    this.drawNavChart();
    this.assetService.getByCode('VIF_CCQ').pipe(first()).subscribe((res: any) => {
      // console.log("res: ", res);
      this.asset = res.data;
    });
  }

  search() {
    this.dashboardService.getNavReport(this.customerSelectedId, formatDate(this.fromDate), formatDate(this.toDate)).pipe(first()).subscribe(res => {
      this.navList = res;
    });
  }

  drawNavChart(){
    this.dashboardService.getNavChartData(this.isByMonth).pipe(first()).subscribe(res => {
      this.navChartDataLst = res;
      Object.keys(this.navChartDataLst).forEach(key => {
        this.lineChartLabels.push(this.navChartDataLst[key].key);
        this.lineChartData[0].data.push(this.navChartDataLst[key].value.toFixed(2));
      });
    });
  }

  // events
  public chartClicked(e: any): void {
    // console.log(e);
  }

  public chartHovered(e: any): void {
    // console.log(e);
  }

  setradio(radioValue: number){
    this.lineChartData = [
      { data: [], label: 'NAV' }
    ];
    this.lineChartLabels = [];
    if(radioValue === 1){
      this.isByMonth = true;
    }else{
      this.isByMonth = false;
    }
    this.drawNavChart();
  }
}


