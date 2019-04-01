import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { DashboardComponent } from './dashboard.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { GeneralScreenComponent } from './general.screen.component';
import { ReportAssetScreenComponent } from './report.asset.component';
import { AppTranslationModule } from '../../app.translation.module';
import { CommonModule } from '@angular/common';


@NgModule({
  imports: [
    FormsModule,
    DashboardRoutingModule,
    ChartsModule,
    BsDropdownModule,
    ButtonsModule.forRoot(),
    TabsModule,
    AppTranslationModule,
    ReactiveFormsModule,
    CommonModule
  ],
  declarations: [ DashboardComponent, GeneralScreenComponent, ReportAssetScreenComponent ]
})
export class DashboardModule { }
