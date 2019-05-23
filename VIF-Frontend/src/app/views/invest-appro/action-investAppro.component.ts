import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';
import { Customer } from '../../models/Customer.model';
import { FormBuilder } from '@angular/forms';
import { Pager } from '../../models/Pager';
import { InvestAppro } from '../../models/InvestAppro.model'
import { InvestApproService } from '../../services/investAppro.service';
import { formatDate } from '../../helpers/function.share';
import { CustomerService } from '../../services/customer.service';
import { BsDatepickerConfig } from 'ngx-bootstrap';

@Component({
  templateUrl: 'action-investAppro.component.html',

})
export class ActionInvestApproComponent implements OnInit {
  ngOnInit(): void {
    throw new Error("Method not implemented.");
  }

}