import { element, by, ElementFinder } from 'protractor';

export class NotificacionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-notificacion div table .btn-danger'));
  title = element.all(by.css('jhi-notificacion div h2#page-heading span')).first();
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

export class NotificacionUpdatePage {
  pageTitle = element(by.id('jhi-notificacion-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fechaInput = element(by.id('field_fecha'));
  detallesInput = element(by.id('field_detalles'));
  fechaEnvioInput = element(by.id('field_fechaEnvio'));
  tipoSelect = element(by.id('field_tipo'));
  clienteIdInput = element(by.id('field_clienteId'));
  productoIdInput = element(by.id('field_productoId'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async setFechaEnvioInput(fechaEnvio: string): Promise<void> {
    await this.fechaEnvioInput.sendKeys(fechaEnvio);
  }

  async getFechaEnvioInput(): Promise<string> {
    return await this.fechaEnvioInput.getAttribute('value');
  }

  async setTipoSelect(tipo: string): Promise<void> {
    await this.tipoSelect.sendKeys(tipo);
  }

  async getTipoSelect(): Promise<string> {
    return await this.tipoSelect.element(by.css('option:checked')).getText();
  }

  async tipoSelectLastOption(): Promise<void> {
    await this.tipoSelect.all(by.tagName('option')).last().click();
  }

  async setClienteIdInput(clienteId: string): Promise<void> {
    await this.clienteIdInput.sendKeys(clienteId);
  }

  async getClienteIdInput(): Promise<string> {
    return await this.clienteIdInput.getAttribute('value');
  }

  async setProductoIdInput(productoId: string): Promise<void> {
    await this.productoIdInput.sendKeys(productoId);
  }

  async getProductoIdInput(): Promise<string> {
    return await this.productoIdInput.getAttribute('value');
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

export class NotificacionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-notificacion-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-notificacion'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
