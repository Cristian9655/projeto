
#!/usr/bin/env bash
set -Eeuo pipefail

# ==========================================================
# GLOBAL SOLUTION 2026 - ZELADORIA URBANA
# Provisionamento da infraestrutura no Microsoft Azure
# Executar no Azure Cloud Shell (Bash)
# ==========================================================

SUBSCRIPTION="Azure for Students"
RG="rg-zeladoria-rm550509"
LOCATION="brazilsouth"

SQL_SERVER="sql-zeladoria-rm550509"
SQL_DB="db-zeladoria"
SQL_ADMIN="sqladmin"

PLAN="plan-zeladoria-rm550509"
APP="app-zeladoria-rm550509"
RUNTIME="JAVA:21-java21"

echo "=========================================="
echo "  INFRAESTRUTURA AZURE - ZELADORIA URBANA"
echo "=========================================="

# 1. Selecionar assinatura
echo ""
echo "[1/9] Selecionando assinatura Azure..."
az account set --subscription "$SUBSCRIPTION"
az account show --query "{Nome:name,Estado:state}" -o table

# 2. Criar ou reutilizar Resource Group
echo ""
echo "[2/9] Configurando Resource Group..."
az group create \
  --name "$RG" \
  --location "$LOCATION" \
  --output none

# 3. Solicitar senha do SQL Server
echo ""
echo "[3/9] Credenciais do Azure SQL..."
echo "Informe a senha do usuario '$SQL_ADMIN'."
echo "Se o servidor ja existe, utilize a senha atual."

read -r -s -p "Senha do SQL Server: " SQL_PASSWORD
echo

if [[ -z "$SQL_PASSWORD" ]]; then
  echo "ERRO: a senha nao pode estar vazia."
  exit 1
fi

# 4. Criar ou reutilizar SQL Server
echo ""
echo "[4/9] Configurando Azure SQL Server..."

if az sql server show \
  --name "$SQL_SERVER" \
  --resource-group "$RG" \
  --output none 2>/dev/null; then

  echo "SQL Server ja existe. Reutilizando..."

else
  az sql server create \
    --name "$SQL_SERVER" \
    --resource-group "$RG" \
    --location "$LOCATION" \
    --admin-user "$SQL_ADMIN" \
    --admin-password "$SQL_PASSWORD" \
    --output none
fi

# 5. Criar ou reutilizar banco de dados
echo ""
echo "[5/9] Configurando Azure SQL Database..."

if az sql db show \
  --resource-group "$RG" \
  --server "$SQL_SERVER" \
  --name "$SQL_DB" \
  --output none 2>/dev/null; then

  echo "Banco de dados ja existe. Reutilizando..."

else
  az sql db create \
    --resource-group "$RG" \
    --server "$SQL_SERVER" \
    --name "$SQL_DB" \
    --edition GeneralPurpose \
    --family Gen5 \
    --capacity 2 \
    --compute-model Serverless \
    --use-free-limit \
    --free-limit-exhaustion-behavior AutoPause \
    --backup-storage-redundancy Local \
    --output none
fi

# 6. Criar ou reutilizar App Service Plan
echo ""
echo "[6/9] Configurando App Service Plan..."

if az appservice plan show \
  --name "$PLAN" \
  --resource-group "$RG" \
  --output none 2>/dev/null; then

  echo "App Service Plan ja existe. Reutilizando..."

else
  az appservice plan create \
    --name "$PLAN" \
    --resource-group "$RG" \
    --location "$LOCATION" \
    --sku F1 \
    --is-linux \
    --output none
fi

# 7. Criar ou reutilizar Web App
echo ""
echo "[7/9] Configurando Azure Web App..."

if az webapp show \
  --resource-group "$RG" \
  --name "$APP" \
  --output none 2>/dev/null; then

  echo "Web App ja existe. Reutilizando..."

else
  az webapp create \
    --resource-group "$RG" \
    --plan "$PLAN" \
    --name "$APP" \
    --runtime "$RUNTIME" \
    --output none
fi

# Exigir HTTPS
az webapp update \
  --resource-group "$RG" \
  --name "$APP" \
  --https-only true \
  --output none

# 8. Configurar firewall do Azure SQL
echo ""
echo "[8/9] Configurando firewall do SQL Server..."

az sql server firewall-rule create \
  --resource-group "$RG" \
  --server "$SQL_SERVER" \
  --name "AllowAzureServices" \
  --start-ip-address 0.0.0.0 \
  --end-ip-address 0.0.0.0 \
  --output none

# 9. Configurar conexao Spring Boot
echo ""
echo "[9/9] Configurando variaveis do Spring Boot..."

JDBC_URL="jdbc:sqlserver://${SQL_SERVER}.database.windows.net:1433;databaseName=${SQL_DB};encrypt=true;trustServerCertificate=false;loginTimeout=30"

az webapp config appsettings set \
  --resource-group "$RG" \
  --name "$APP" \
  --settings \
    "SPRING_DATASOURCE_URL=$JDBC_URL" \
    "SPRING_DATASOURCE_USERNAME=$SQL_ADMIN" \
    "SPRING_DATASOURCE_PASSWORD=$SQL_PASSWORD" \
    "SPRING_JPA_HIBERNATE_DDL_AUTO=update" \
    "SPRING_JPA_OPEN_IN_VIEW=false" \
    "SPRING_H2_CONSOLE_ENABLED=false" \
  --output none

unset SQL_PASSWORD

# Exibir resumo da infraestrutura
echo ""
echo "=========================================="
echo "       INFRAESTRUTURA CONFIGURADA"
echo "=========================================="

echo ""
echo "Resource Group:"
az group show \
  --name "$RG" \
  --query "{Nome:name,Regiao:location}" \
  --output table

echo ""
echo "SQL Server:"
az sql server show \
  --name "$SQL_SERVER" \
  --resource-group "$RG" \
  --query "{Nome:name,Estado:state}" \
  --output table

echo ""
echo "Banco de dados:"
az sql db show \
  --resource-group "$RG" \
  --server "$SQL_SERVER" \
  --name "$SQL_DB" \
  --query "{Nome:name,Estado:status,SKU:sku.name}" \
  --output table

echo ""
echo "App Service Plan:"
az appservice plan show \
  --name "$PLAN" \
  --resource-group "$RG" \
  --query "{Nome:name,SKU:sku.name}" \
  --output table

echo ""
echo "Web App:"
az webapp show \
  --resource-group "$RG" \
  --name "$APP" \
  --query "{Nome:name,Estado:state,URL:defaultHostName}" \
  --output table

echo ""
echo "URL: https://${APP}.azurewebsites.net"
echo ""
echo "Infraestrutura pronta."
echo "O deploy do JAR e realizado pelo Azure DevOps."
