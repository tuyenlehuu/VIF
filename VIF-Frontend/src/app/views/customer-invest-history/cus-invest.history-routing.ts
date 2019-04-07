import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CusInvestHistoryComponent } from './cus-invest.history.component';

const routes: Routes = [
  {
    path: '',
    component: CusInvestHistoryComponent,
    data: {
      title: 'Lịch sử đầu tư NĐT' 
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CusInvestHistoryRouting {}
