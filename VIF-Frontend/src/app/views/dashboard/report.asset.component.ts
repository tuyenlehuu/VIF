import { Component, OnInit, Input, AfterViewChecked } from '@angular/core';
import { Router } from '@angular/router';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import { User } from '../../models/User.model';
import { DashboardService } from '../../services/dashboard.service';
import { first } from 'rxjs/operators';
import { Dashboard } from '../../models/Dashboard.model';

@Component({
    selector: 'report-asset-screen',
    templateUrl: 'report.asset.component.html'
})
export class ReportAssetScreenComponent implements OnInit {
  users: User[] = [];
  dashboard: Dashboard;

  // Pie tien - ck
  public pieChartLabels: string[] = ['Tiền', 'Chứng khoán'];
  public perMoney;
  public pieChartData: number[] = [];
  public pieChartType = 'pie';

  // pie no
  public pieChartLabelsNo: string[] = ['Nợ', 'Tài sản ròng'];
  public perMoneyNo;
  public pieChartDataNo: number[] = [];

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

  // Line NAV
  // lineChart
  public lineChartData: Array<any> = [
    {data: [10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14], label:'NAV'}
  ];
  public lineChartLabels: Array<any> = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
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


  constructor(private dashboardService:DashboardService){
    this.dashboardService.getData().pipe(first()).subscribe(dashboard=>{
      this.dashboard = dashboard;
      if(this.dashboard){
        if(this.dashboard.shares + this.dashboard.itRt+this.dashboard.cash + this.dashboard.cs>0){
          let pmoney = 100*(this.dashboard.cash + this.dashboard.cs)/(this.dashboard.shares + this.dashboard.itRt+this.dashboard.cash + this.dashboard.cs);
          this.perMoney = pmoney.toFixed(2);
          this.pieChartData= [this.perMoney, 100-this.perMoney];
        }else{
          this.pieChartData= [100, 0];
        }
        
        // no
        if(this.dashboard.totalAsset>0){
          let pmoneyNo = 100*this.dashboard.totalDebt/this.dashboard.totalAsset;
          this.perMoneyNo = pmoneyNo.toFixed(2);
          this.pieChartDataNo= [this.perMoneyNo, 100-this.perMoneyNo];
        }else{
          this.pieChartDataNo= [0, 100];
        }
      }
    });
  }

  ngOnInit(): void {
    // generate random values for mainChart
    // this.userService.getAll().pipe(first()).subscribe(users=>{
    //   this.users = users;
    //   console.log("all user is: ", users);
    // });
    
  }

  
  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }
  

}


