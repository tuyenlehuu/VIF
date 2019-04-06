import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InvestorTransComponent } from './investor-trans.component';

const routes: Routes = [
  {
    path: '',
    component: InvestorTransComponent,
    data: {
      title: 'Giao dịch NĐT' 
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InvestTransRouting {}
