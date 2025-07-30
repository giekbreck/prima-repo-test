
// prova commit tramite vscode

<<<<<<< HEAD
// Modifica parallela su main
=======
// modifica fatta da giek
>>>>>>> modifica-da-giacomo

// linea di modifica per test git bash

// a partire dal codice fornito vado a modificare per ottenere l'output richiesto

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{Row, DataFrame}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// modififico la data iniziale con quell richiesta
import spark.implicits._
val today = LocalDate.now()
val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val startDate = LocalDate.parse("2025-06-20", dateFormatter)
val startDateStr = startDate.format(dateFormatter)
val endDateStr = today.format(dateFormatter)

val static_query = """ 
select *
from w_unipolsai_ddi_digital.digital_storytelling_vi_static_mapping
"""
val df_mapping = spark.sql(static_query)

// vado a filtrare nella query per il pagename anche in base agli uri richiesti
val query1 = f"""
select 
    REGEXP_EXTRACT(uri, '^.{39}([^/?]+)',1) as uri_page,
    REGEXP_EXTRACT(dom_value, 'page_name=([^;]+);', 1) as page_name,
    REGEXP_EXTRACT(dom_value, 'proposal_dx_source=([^;]+);', 1) as proposal_dx_source,
    REGEXP_EXTRACT(dom_value, 'proposal_dx_category=([^;]+);', 1) as proposal_dx_category,
    client_action,
    dom_value,
    uri,
    action_visual_name,
    client_timestamp,
    action_timestamp,
    session_uuid,
    sequence
FROM
    ho_unipolsai_ddi.unipolsai_digital_glassbox_tpdweb_uas
where
    app_id = 18
    AND CONCAT(anno,'-', mese,'-', giorno) >= '$startDateStr'
    AND CONCAT(anno,'-', mese,'-', giorno) < '$endDateStr'
    AND client_action = 'thirdpartymap'
    AND (
        uri = 'https://www.unipol.it/prodotto-unico' OR
        uri = 'https://www.unipol.it/unica-preventivatore'
    )
ORDER BY session_uuid, client_timestamp
"""
val df_PU = spark.sql(query1)
// Definisci la colonna id
val df_PU_withId = df_PU.withColumn("id", concat_ws("_", $"session_uuid", $"client_action", $"client_timestamp", $"sequence"))
// Filtra solo le righe con il client_action di interesse
//val df_filtered_grouped = df_PU_withId.filter($"page_name".isNotNull && trim($"page_name") =!= "")
// Copia il page_name di thirdpartymap nella riga precedente
val windowSpec = Window.partitionBy("session_uuid").orderBy("client_timestamp")
val df_merged = df_filtered_grouped
  .withColumn("page_name", when(
    lead("client_action", 1).over(windowSpec) === "thirdpartymap", 
    lead("page_name", 1).over(windowSpec)
  ).otherwise($"page_name"))
  .withColumn("proposal_dx_source", when(
    lead("client_action", 1).over(windowSpec) === "thirdpartymap", 
    lead("proposal_dx_source", 1).over(windowSpec)
  ).otherwise($"proposal_dx_source"))
  .withColumn("proposal_dx_category", when(
    lead("client_action", 1).over(windowSpec) === "thirdpartymap", 
    lead("proposal_dx_category", 1).over(windowSpec)
  ).otherwise($"proposal_dx_category"))

  
// Query per "webStruggle"
val query2 = f"""
select 
    REGEXP_EXTRACT(uri, '^.{39}([^/?]+)',1) as uri_page,
    client_action,
    dom_value,
    uri,
    action_visual_name,
    client_timestamp,
    action_timestamp,
    session_uuid,
    sequence
FROM
    ho_unipolsai_ddi.unipolsai_digital_glassbox_tpdweb_uas
where
    app_id = 18
    AND CONCAT(anno,'-', mese,'-', giorno) >= '$startDateStr'
    AND CONCAT(anno,'-', mese,'-', giorno) < '$endDateStr'
    and client_action = 'webStruggle'
order by session_uuid, client_timestamp
"""
val df_ws = spark.sql(query2)
// Converti le colonne timestamp
val df_merged_with_ts = df_merged.withColumn("client_timestamp", to_timestamp(col("client_timestamp")))
val df_ws_with_ts = df_ws.withColumn("client_timestamp", to_timestamp(col("client_timestamp")))
// Pre-filtra e prepara i dati per il join
val maxTsDf = df_merged_with_ts
  .groupBy("session_uuid", "uri")
  .agg(
    max("client_timestamp").as("max_timestamp"),
    first("page_name").as("page_name"),
    first("proposal_dx_source").as("proposal_dx_source"),
    first("proposal_dx_category").as("proposal_dx_category")
    //first("policy_proposal_source").as("policy_proposal_source"),
    //first("policy_type").as("policy_type")
  )
// Unisci i dati pre-filtrati con il DataFrame originale
val dfWs = df_ws_with_ts.as("ws")
  .join(
    maxTsDf.as("maxTs"),
    $"ws.session_uuid" === $"maxTs.session_uuid" && $"ws.uri" === $"maxTs.uri" && $"ws.client_timestamp" < $"maxTs.max_timestamp",
    "left"
  )
  .select(
    $"ws.*",
    $"maxTs.page_name",
    $"maxTs.proposal_dx_source",
    $"maxTs.proposal_dx_category"
    //
    //$"maxTs.policy_proposal_source",
    //$"maxTs.policy_type"
  )
val dfWs4 = dfWs.withColumn("dom_value", regexp_extract($"dom_value", "WebStruggleDetails\\{type=(.*)\\}", 1))
val df_filtered = dfWs4.filter($"page_name".isNotNull && trim($"page_name") =!= "").select($"client_timestamp",$"dom_value",$"page_name",$"proposal_dx_source",$"proposal_dx_category")
// ERROR COUNT
val df_with_month = df_filtered.withColumn("month", date_format($"client_timestamp", "yyyy-MM"))
val df_mapping_pet = df_mapping.filter($"prodotto" === "pet")
val df_vendita_ibrida_pet = df_mapping.join(df_with_month, df_mapping_pet("mapping_name") === df_with_month("page_name"))
val struggle_pet_df = df_vendita_ibrida_pet.groupBy($"month", $"dom_value", $"event_raw", $"proposal_dx_source",$"proposal_dx_category").agg(count($"dom_value").as("struggle_count"))
val df_mapping_viaggi = df_mapping.filter($"prodotto" === "viaggi")
val df_vendita_ibrida_viaggi = df_mapping_viaggi.join(df_with_month, df_mapping_pet("mapping_name") === df_with_month("page_name"))
val struggle_viaggi_df = df_vendita_ibrida_viaggi.groupBy($"month", $"dom_value", $"event_raw", $"proposal_dx_source",$"proposal_dx_category").agg(count($"dom_value").as("struggle_count"))
val df_mapping_casa = df_mapping.filter($"prodotto" === "casa")
val df_vendita_ibrida_casa = df_mapping_casa.join(df_with_month, df_mapping_pet("mapping_name") === df_with_month("page_name"))
val struggle_casa_df = df_vendita_ibrida_casa.groupBy($"month", $"dom_value", $"event_raw", $"proposal_dx_source",$"proposal_dx_category").agg(count($"dom_value").as("struggle_count"))
val struggle = struggle_casa_df.union(struggle_viaggi_df).union(struggle_pet_df).dropDuplicates()
// Salva il risultato finale


// Salva il risultato finale
struggle.show(100, truncate = false)

spark.stop()