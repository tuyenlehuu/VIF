import { Component, OnInit } from '@angular/core';
import { Asset } from '../../models/Asset.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AssetService } from '../../services/asset.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { GroupAsset } from '../../models/GroupAsset.model';
import { first } from 'rxjs/operators';
import { Branch } from '../../models/Branch.model';
import { BranchService } from '../../services/branch.service';
import { config } from '../../config/application.config';

@Component({
  selector: 'app-other.asset',
  templateUrl: './create.edit.asset.component.html',
  styleUrls: ['./asset.component.scss']
})
export class CEAssetComponent implements OnInit {
  asset: Asset;
  sub: any;
  id: any;
  addAssetForm: FormGroup;
  submitted = false;
  editAssetForm: FormGroup;
  groupAssets: GroupAsset[] = [];
  branchShares: Branch[] = [];
  branchCodeSelected: string;
  newAssetToAdd: Asset = new Asset();
  groupAssetSelectedId: number = 2;

  constructor(private route: ActivatedRoute, private assetService: AssetService, private router: Router,
    private toastrService: ToastrService, private fb: FormBuilder, private translateService: TranslateService,
    private branchService: BranchService) {
    this.assetService.getGroupAsset().pipe(first()).subscribe((res: any) => {
      this.groupAssets = res.data;
    });
    this.branchService.getAll().pipe(first()).subscribe((res: any) => {
      this.branchShares = res;
      if (this.branchShares.length > 0) {
        this.branchCodeSelected = this.branchShares[0].branchCode;
        this.createForm();
      }
    });
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    if (this.id > 0) {
      this.assetService.getAssetById(this.id).subscribe((res: any) => {
        this.asset = res.data;
        // console.log("current user: ", this.user);
        if (this.asset.groupAsset.id === 2) {
          this.groupAssetSelectedId = 2;
        } else {
          this.groupAssetSelectedId = 4;
        }
        this.createEditForm();
      })
    } else {
      this.asset = new Asset();
    }
  }

  createForm() {
    this.addAssetForm = this.fb.group({
      groupAsset: [2, Validators.required],
      assetCode: ['', Validators.required],
      amount: [0],
      assetName: ['', Validators.required],
      currentPrice: [0, Validators.required],
      description: [''],
      orginalPrice: [0],
      branchCode: [this.branchCodeSelected != null ? this.branchCodeSelected : '']
    });
  }

  createEditForm() {
    this.editAssetForm = this.fb.group({
      eGroupAsset: [this.asset.groupAsset != null ? this.asset.groupAsset.id : 0, Validators.required],
      eAssetCode: [this.asset.assetCode, Validators.required],
      eAmount: [this.asset.amount],
      eAssetName: [this.asset.assetName, Validators.required],
      eCurrentPrice: [this.asset.currentPrice, Validators.required],
      eDescription: [this.asset.description],
      eOrginalPrice: [this.asset.orginalPrice],
      eBranchCode: [this.asset.branchCode]
    });
  }

  onAddSubmit() {
    if (this.addAssetForm.invalid) {
      return;
    }

    if (this.addAssetForm.value.groupAsset == 2 && !this.addAssetForm.value.branchCode) {
      return;
    }

    if (this.addAssetForm.value.groupAsset == 4) {
      this.newAssetToAdd.branchCode = null;
    } else {
      this.newAssetToAdd.branchCode = this.addAssetForm.value.branchCode;
    }

    this.newAssetToAdd.activeFlg = 1;
    this.newAssetToAdd.amount = this.addAssetForm.value.amount != null ? this.addAssetForm.value.amount : 0;
    this.newAssetToAdd.assetCode = this.addAssetForm.value.assetCode;
    this.newAssetToAdd.assetName = this.addAssetForm.value.assetName;
    this.newAssetToAdd.currentPrice = this.addAssetForm.value.currentPrice != null ? this.addAssetForm.value.currentPrice : 0;
    this.newAssetToAdd.description = this.addAssetForm.value.description;
    this.newAssetToAdd.orginalPrice = this.addAssetForm.value.orginalPrice != null ? this.addAssetForm.value.orginalPrice : 0;



    let mGroup: GroupAsset = new GroupAsset();
    mGroup.id = this.groupAssetSelectedId;
    mGroup.typeOfAsset = this.groupAssetSelectedId;
    this.newAssetToAdd.groupAsset = mGroup;

    this.assetService.addAsset(this.newAssetToAdd).pipe(first()).subscribe((respons: any) => {
      if (respons.code === 409) {
        this.translateService.get('vif.message.asset_exists').subscribe((res: string) => {
          this.showError(res);
        });
      } else if (respons.code === 200) {
        this.translateService.get('vif.message.create_success').subscribe((res: string) => {
          this.showSuccess(res);
        });
        this.router.navigate(['/asset-management']);
      }
    }, (err) => {
      this.translateService.get('vif.message.create_failed').subscribe((res: string) => {
        this.showError(res);
      });
      // console.log(err);
    });

  }

  onEditSubmit() {
    if (this.editAssetForm.invalid) {
      return;
    }

    if (this.editAssetForm.value.eGroupAsset == 2 && !this.editAssetForm.value.eBranchCode) {
      return;
    }

    let mGroup: GroupAsset = new GroupAsset();
    mGroup.id = this.editAssetForm.value.eGroupAsset;
    this.asset.groupAsset = mGroup;
    this.asset.assetCode = this.editAssetForm.value.eAssetCode;
    this.asset.amount = this.editAssetForm.value.eAmount;
    this.asset.assetName = this.editAssetForm.value.eAssetName;
    this.asset.currentPrice = this.editAssetForm.value.eCurrentPrice;
    this.asset.description = this.editAssetForm.value.eDescription;
    this.asset.orginalPrice = this.editAssetForm.value.eOrginalPrice;
    this.asset.branchCode = this.editAssetForm.value.eGroupAsset == 2 ? this.editAssetForm.value.eBranchCode : null;
    // console.log("my Asset: ", this.asset);

    this.assetService.update(this.asset).subscribe(res => {
      // console.log("new user: ", res);
      this.translateService.get('vif.message.update_success').subscribe((res: string) => {
        this.showSuccess(res);
      });
      this.router.navigate(['/asset-management']);
    }, (err) => {
      this.translateService.get('vif.message.update_failed').subscribe((res: string) => {
        this.showError(res);
      });
      // console.log(err);
    });
  }

  get addForm() { return this.addAssetForm.controls; }

  get editForm() { return this.editAssetForm.controls; }

  showSuccess(mes: string) {
    this.toastrService.success('', mes, {
      timeOut: config.timeoutToast
    });
  }

  showError(mes: string) {
    this.toastrService.error('', mes, {
      timeOut: config.timeoutToast
    });
  }


  onChangeTypeAsset() {
    this.groupAssetSelectedId = this.addAssetForm.value.groupAsset;
    if (this.groupAssetSelectedId == 4) {
      this.newAssetToAdd.branchCode = null;
    } else {
      this.newAssetToAdd.branchCode = this.branchCodeSelected;
    }
    let mGroup: GroupAsset = new GroupAsset();
    mGroup.id = this.groupAssetSelectedId;
    mGroup.typeOfAsset = this.groupAssetSelectedId;
    this.newAssetToAdd.groupAsset = mGroup;
  }

  onChangeTypeAssetEdit() {
    this.groupAssetSelectedId = this.editAssetForm.value.eGroupAsset;
    if (this.editAssetForm.value.eGroupAsset == 2) {
      this.editForm.eBranchCode.setValue(this.branchShares[0].branchCode);
    } else {
      this.editForm.eBranchCode.setValue(null);
    }
  }

}
