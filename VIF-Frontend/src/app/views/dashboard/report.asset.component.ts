import { Component, OnInit, Input, AfterViewChecked } from '@angular/core';
import { Router } from '@angular/router';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import { User } from '../../models/User.model';
import { DashboardService } from '../../services/dashboard.service';
import { first } from 'rxjs/operators';
import { Dashboard } from '../../models/Dashboard.model';
import { KeyNameValue } from '../../models/KeyNameValue.model';

@Component({
    selector: 'report-asset-screen',
    templateUrl: 'report.asset.component.html'
})
export class ReportAssetScreenComponent implements OnInit {
  users: User[] = [];
  dashboard: Dashboard;
  assetsReport: KeyNameValue [] = [];

  // Pie tien - ck
  public pieChartLabels: string[] = ['Tiền', 'Chứng khoán'];
  public perMoney;
  public pieChartData: number[] = [];
  public pieChartType = 'pie';

  // pie no
  public pieChartLabelsNo: string[] = ['Nợ', 'Tài sản ròng'];
  public perMoneyNo;
  public pieChartDataNo: number[] = [];

  // barChart
  public barChartOptions: any = {
    scaleShowVerticalLines: false,
    responsive: true
  };
  public barChartLabels: string[] = [];
  public barChartType = 'bar';
  public barChartLegend = true;

  public barChartData: any[] = this.barChartData = [
    {data: [], label: 'Tài sản'}
  ];

  public pieChartOptions:any = {
    tooltips: {
      enabled: true,
      mode: 'single',
      callbacks: {
        label: function(tooltipItem, data) {
          var allData = data.datasets[tooltipItem.datasetIndex].data;
          var tooltipLabel = data.labels[tooltipItem.index];
          var tooltipData = allData[tooltipItem.index];
          return tooltipLabel + ": " + tooltipData + "%";
        }
      }
    }
  };

  constructor(private dashboardService:DashboardService){
    this.dashboardService.getData().pipe(first()).subscribe(dashboard=>{
      this.dashboard = dashboard;
      if(this.dashboard){
        if(this.dashboard.shares + this.dashboard.itRt+this.dashboard.cash + this.dashboard.cs>0){
          let pmoney = 100*(this.dashboard.cash + this.dashboard.cs)/(this.dashboard.shares + this.dashboard.itRt+this.dashboard.cash + this.dashboard.cs);
          this.perMoney = pmoney.toFixed(2);
          this.pieChartData= [this.perMoney, (100-this.perMoney).toFixed(2)];
        }else{
          this.pieChartData= [100, 0];
        }
        
        // no
        if(this.dashboard.totalAsset>0){
          let pmoneyNo = 100*this.dashboard.totalDebt/this.dashboard.totalAsset;
          this.perMoneyNo = pmoneyNo.toFixed(2);
          this.pieChartDataNo= [this.perMoneyNo, (100-this.perMoneyNo).toFixed(2)];
        }else{
          this.pieChartDataNo= [0, 100];
        }
      }
    });
  }

  ngOnInit(): void {
    this.dashboardService.getReportAsset().pipe(first()).subscribe(res=>{
      this.assetsReport = res;
      Object.keys(this.assetsReport).forEach(key => {
        this.barChartLabels.push(this.assetsReport[key].key);
        this.barChartData[0].data.push(this.assetsReport[key].value.toFixed(2));
      });
    });
  }

  
  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }
  

}


