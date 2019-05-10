import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InvestApproComponent } from './investAppro.component';

const routes: Routes = [
  {
    path: '',
    component: InvestApproComponent,
    data: {
      title: 'Phê duyệt đầu tư' 
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InvestApproRouting {}