import { NgModule,ModuleWithProviders} from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InvestComponent } from './invest.component';



const routes: Routes = [
  {
    path: '',
    component: InvestComponent,
    data: {
      title: 'Quản lý đầu tư'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InvestRoutingModule {}
