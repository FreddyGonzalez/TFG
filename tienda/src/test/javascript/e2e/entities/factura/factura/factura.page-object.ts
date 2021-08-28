import { element, by, ElementFinder } from 'protractor';

export class FacturaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-factura div table .btn-danger'));
  title = element.all(by.css('jhi-factura div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class FacturaUpdatePage {
  pageTitle = element(by.id('jhi-factura-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idFacturaInput = element(by.id('field_idFactura'));
  fechaInput = element(by.id('field_fecha'));
  detallesInput = element(by.id('field_detalles'));
  estadoSelect = element(by.id('field_estado'));
  metodoPagoSelect = element(by.id('field_metodoPago'));
  fechaPagoInput = element(by.id('field_fechaPago'));
  totalInput = element(by.id('field_total'));
  idPedidoProductoInput = element(by.id('field_idPedidoProducto'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdFacturaInput(idFactura: string): Promise<void> {
    await this.idFacturaInput.sendKeys(idFactura);
  }

  async getIdFacturaInput(): Promise<string> {
    return await this.idFacturaInput.getAttribute('value');
  }

  async setFechaInput(fecha: string): Promise<void> {
    await this.fechaInput.sendKeys(fecha);
  }

  async getFechaInput(): Promise<string> {
    return await this.fechaInput.getAttribute('value');
  }

  async setDetallesInput(detalles: string): Promise<void> {
    await this.detallesInput.sendKeys(detalles);
  }

  async getDetallesInput(): Promise<string> {
    return await this.detallesInput.getAttribute('value');
  }

  async setEstadoSelect(estado: string): Promise<void> {
    await this.estadoSelect.sendKeys(estado);
  }

  async getEstadoSelect(): Promise<string> {
    return await this.estadoSelect.element(by.css('option:checked')).getText();
  }

  async estadoSelectLastOption(): Promise<void> {
    await this.estadoSelect.all(by.tagName('option')).last().click();
  }

  async setMetodoPagoSelect(metodoPago: string): Promise<void> {
    await this.metodoPagoSelect.sendKeys(metodoPago);
  }

  async getMetodoPagoSelect(): Promise<string> {
    return await this.metodoPagoSelect.element(by.css('option:checked')).getText();
  }

  async metodoPagoSelectLastOption(): Promise<void> {
    await this.metodoPagoSelect.all(by.tagName('option')).last().click();
  }

  async setFechaPagoInput(fechaPago: string): Promise<void> {
    await this.fechaPagoInput.sendKeys(fechaPago);
  }

  async getFechaPagoInput(): Promise<string> {
    return await this.fechaPagoInput.getAttribute('value');
  }

  async setTotalInput(total: string): Promise<void> {
    await this.totalInput.sendKeys(total);
  }

  async getTotalInput(): Promise<string> {
    return await this.totalInput.getAttribute('value');
  }

  async setIdPedidoProductoInput(idPedidoProducto: string): Promise<void> {
    await this.idPedidoProductoInput.sendKeys(idPedidoProducto);
  }

  async getIdPedidoProductoInput(): Promise<string> {
    return await this.idPedidoProductoInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class FacturaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-factura-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-factura'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
