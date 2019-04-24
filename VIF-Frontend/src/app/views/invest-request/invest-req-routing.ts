import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InvestRequestComponent } from './invest-req.component';

const routes: Routes = [
  {
    path: '',
    component: InvestRequestComponent,
    data: {
      title: 'Đăng kí đầu tư' 
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InvestRequestRouting {}
