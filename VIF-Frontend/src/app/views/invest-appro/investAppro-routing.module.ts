import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InvestApproComponent } from './investAppro.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Phê duyệt đầu tư' 
    },
    children: [
      {
        path: '',
        redirectTo: 'invest-appro'
      },
      {
        path: 'invest-appro',
        component: InvestApproComponent,
        data: {
          title: ''
        }
      },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InvestApproRouting {}