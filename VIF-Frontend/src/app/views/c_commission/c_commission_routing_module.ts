import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CompanyCommissionComponent } from './c_commission_component';

const routes: Routes = [
  {
    path: '',
    component: CompanyCommissionComponent,
    data: {
      title: 'Chia lợi tức cho công ty'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompanyCommissionRoutingModule { }
