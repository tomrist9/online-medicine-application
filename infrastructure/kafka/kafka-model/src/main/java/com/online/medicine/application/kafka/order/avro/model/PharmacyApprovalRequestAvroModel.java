/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.food.ordering.system.kafka.order.avro.model;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class PharmacyApprovalRequestAvroModel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -3917361261016430486L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"RestaurantApprovalRequestAvroModel\",\"namespace\":\"com.food.ordering.system.kafka.order.avro.model\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"sagaId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"restaurantId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"orderId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"restaurantOrderStatus\",\"type\":{\"type\":\"enum\",\"name\":\"RestaurantOrderStatus\",\"symbols\":[\"PAID\"]}},{\"name\":\"products\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Product\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"logicalType\":\"uuid\"},{\"name\":\"quantity\",\"type\":\"int\"}]}}},{\"name\":\"price\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":10,\"scale\":2}},{\"name\":\"createdAt\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.TimestampMillisConversion());
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.DecimalConversion());
  }

  private static final BinaryMessageEncoder<PharmacyApprovalRequestAvroModel> ENCODER =
      new BinaryMessageEncoder<PharmacyApprovalRequestAvroModel>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<PharmacyApprovalRequestAvroModel> DECODER =
      new BinaryMessageDecoder<PharmacyApprovalRequestAvroModel>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<PharmacyApprovalRequestAvroModel> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<PharmacyApprovalRequestAvroModel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<PharmacyApprovalRequestAvroModel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<PharmacyApprovalRequestAvroModel>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this RestaurantApprovalRequestAvroModel to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a RestaurantApprovalRequestAvroModel from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a RestaurantApprovalRequestAvroModel instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static PharmacyApprovalRequestAvroModel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String id;
  private java.lang.String sagaId;
  private java.lang.String pharmacyId;
  private java.lang.String orderId;
  private PharmacyOrderStatus pharmacyOrderStatus;
  private java.util.List<Remedy> remedies;
  private java.math.BigDecimal price;
  private java.time.Instant createdAt;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public PharmacyApprovalRequestAvroModel() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param sagaId The new value for sagaId
   * @param pharmacyId The new value for restaurantId
   * @param orderId The new value for orderId
   * @param pharmacyOrderStatus The new value for restaurantOrderStatus
   * @param remedies The new value for products
   * @param price The new value for price
   * @param createdAt The new value for createdAt
   */
  public PharmacyApprovalRequestAvroModel(java.lang.String id, java.lang.String sagaId, java.lang.String pharmacyId, java.lang.String orderId, PharmacyOrderStatus pharmacyOrderStatus, java.util.List<Remedy> remedies, java.math.BigDecimal price, java.time.Instant createdAt) {
    this.id = id;
    this.sagaId = sagaId;
    this.pharmacyId=pharmacyId;
    this.orderId = orderId;
    this.pharmacyOrderStatus = pharmacyOrderStatus;
    this.remedies = remedies;
    this.price = price;
    this.createdAt = createdAt.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return sagaId;
    case 2: return pharmacyId;
    case 3: return orderId;
    case 4: return pharmacyOrderStatus;
    case 5: return remedies;
    case 6: return price;
    case 7: return createdAt;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      null,
      null,
      null,
      null,
      null,
      new org.apache.avro.Conversions.DecimalConversion(),
      new org.apache.avro.data.TimeConversions.TimestampMillisConversion(),
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = value$ != null ? value$.toString() : null; break;
    case 1: sagaId = value$ != null ? value$.toString() : null; break;
    case 2: pharmacyId = value$ != null ? value$.toString() : null; break;
    case 3: orderId = value$ != null ? value$.toString() : null; break;
    case 4: pharmacyOrderStatus = (PharmacyOrderStatus)value$; break;
    case 5: remedies = (java.util.List<Remedy>)value$; break;
    case 6: price = (java.math.BigDecimal)value$; break;
    case 7: createdAt = (java.time.Instant)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.String getId() {
    return id;
  }


  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.String value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'sagaId' field.
   * @return The value of the 'sagaId' field.
   */
  public java.lang.String getSagaId() {
    return sagaId;
  }


  /**
   * Sets the value of the 'sagaId' field.
   * @param value the value to set.
   */
  public void setSagaId(java.lang.String value) {
    this.sagaId = value;
  }

  /**
   * Gets the value of the 'restaurantId' field.
   * @return The value of the 'restaurantId' field.
   */
  public java.lang.String getPharmacyId() {
    return pharmacyId;
  }


  /**
   * Sets the value of the 'restaurantId' field.
   * @param value the value to set.
   */
  public void setPharmacyId(java.lang.String value) {
    this.pharmacyId = value;
  }

  /**
   * Gets the value of the 'orderId' field.
   * @return The value of the 'orderId' field.
   */
  public java.lang.String getOrderId() {
    return orderId;
  }


  /**
   * Sets the value of the 'orderId' field.
   * @param value the value to set.
   */
  public void setOrderId(java.lang.String value) {
    this.orderId = value;
  }

  /**
   * Gets the value of the 'restaurantOrderStatus' field.
   * @return The value of the 'restaurantOrderStatus' field.
   */
  public PharmacyOrderStatus getRestaurantOrderStatus() {
    return pharmacyOrderStatus;
  }


  /**
   * Sets the value of the 'restaurantOrderStatus' field.
   * @param value the value to set.
   */
  public void setRestaurantOrderStatus(PharmacyOrderStatus value) {
    this.pharmacyOrderStatus = value;
  }

  /**
   * Gets the value of the 'products' field.
   * @return The value of the 'products' field.
   */
  public java.util.List<Remedy> getRemedies() {
    return remedies;
  }


  /**
   * Sets the value of the 'products' field.
   * @param value the value to set.
   */
  public void setRemedies(java.util.List<Remedy> value) {
    this.remedies = value;
  }

  /**
   * Gets the value of the 'price' field.
   * @return The value of the 'price' field.
   */
  public java.math.BigDecimal getPrice() {
    return price;
  }


  /**
   * Sets the value of the 'price' field.
   * @param value the value to set.
   */
  public void setPrice(java.math.BigDecimal value) {
    this.price = value;
  }

  /**
   * Gets the value of the 'createdAt' field.
   * @return The value of the 'createdAt' field.
   */
  public java.time.Instant getCreatedAt() {
    return createdAt;
  }


  /**
   * Sets the value of the 'createdAt' field.
   * @param value the value to set.
   */
  public void setCreatedAt(java.time.Instant value) {
    this.createdAt = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  /**
   * Creates a new RestaurantApprovalRequestAvroModel RecordBuilder.
   * @return A new RestaurantApprovalRequestAvroModel RecordBuilder
   */
  public static PharmacyApprovalRequestAvroModel.Builder newBuilder() {
    return new PharmacyApprovalRequestAvroModel.Builder();
  }

  /**
   * Creates a new RestaurantApprovalRequestAvroModel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new RestaurantApprovalRequestAvroModel RecordBuilder
   */
  public static PharmacyApprovalRequestAvroModel.Builder newBuilder(PharmacyApprovalRequestAvroModel.Builder other) {
    if (other == null) {
      return new PharmacyApprovalRequestAvroModel.Builder();
    } else {
      return new PharmacyApprovalRequestAvroModel.Builder(other);
    }
  }

  /**
   * Creates a new RestaurantApprovalRequestAvroModel RecordBuilder by copying an existing RestaurantApprovalRequestAvroModel instance.
   * @param other The existing instance to copy.
   * @return A new RestaurantApprovalRequestAvroModel RecordBuilder
   */
  public static PharmacyApprovalRequestAvroModel.Builder newBuilder(PharmacyApprovalRequestAvroModel other) {
    if (other == null) {
      return new PharmacyApprovalRequestAvroModel.Builder();
    } else {
      return new PharmacyApprovalRequestAvroModel.Builder(other);
    }
  }

  /**
   * RecordBuilder for RestaurantApprovalRequestAvroModel instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PharmacyApprovalRequestAvroModel>
    implements org.apache.avro.data.RecordBuilder<PharmacyApprovalRequestAvroModel> {

    private java.lang.String id;
    private java.lang.String sagaId;
    private java.lang.String pharmacyId;
    private java.lang.String orderId;
    private PharmacyOrderStatus pharmacyOrderStatus;
    private java.util.List<Remedy> remedies;
    private java.math.BigDecimal price;
    private java.time.Instant createdAt;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(PharmacyApprovalRequestAvroModel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.sagaId)) {
        this.sagaId = data().deepCopy(fields()[1].schema(), other.sagaId);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.pharmacyId)) {
        this.pharmacyId = data().deepCopy(fields()[2].schema(), other.pharmacyId);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.orderId)) {
        this.orderId = data().deepCopy(fields()[3].schema(), other.orderId);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.pharmacyOrderStatus)) {
        this.pharmacyOrderStatus = data().deepCopy(fields()[4].schema(), other.pharmacyOrderStatus);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.remedies)) {
        this.remedies = data().deepCopy(fields()[5].schema(), other.remedies);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.price)) {
        this.price = data().deepCopy(fields()[6].schema(), other.price);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[7].schema(), other.createdAt);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
    }

    /**
     * Creates a Builder by copying an existing RestaurantApprovalRequestAvroModel instance
     * @param other The existing instance to copy.
     */
    private Builder(PharmacyApprovalRequestAvroModel other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.sagaId)) {
        this.sagaId = data().deepCopy(fields()[1].schema(), other.sagaId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.pharmacyId)) {
        this.pharmacyId = data().deepCopy(fields()[2].schema(), other.pharmacyId);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.orderId)) {
        this.orderId = data().deepCopy(fields()[3].schema(), other.orderId);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.pharmacyOrderStatus)) {
        this.pharmacyOrderStatus = data().deepCopy(fields()[4].schema(), other.pharmacyOrderStatus);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.remedies)) {
        this.remedies = data().deepCopy(fields()[5].schema(), other.remedies);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.price)) {
        this.price = data().deepCopy(fields()[6].schema(), other.price);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[7].schema(), other.createdAt);
        fieldSetFlags()[7] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.String getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder setId(java.lang.String value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'sagaId' field.
      * @return The value.
      */
    public java.lang.String getSagaId() {
      return sagaId;
    }


    /**
      * Sets the value of the 'sagaId' field.
      * @param value The value of 'sagaId'.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder setSagaId(java.lang.String value) {
      validate(fields()[1], value);
      this.sagaId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'sagaId' field has been set.
      * @return True if the 'sagaId' field has been set, false otherwise.
      */
    public boolean hasSagaId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'sagaId' field.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder clearSagaId() {
      sagaId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'pharmacyId' field.
      * @return The value.
      */
    public java.lang.String getPharmacyId() {
      return pharmacyId;
    }


    /**
      * Sets the value of the 'restaurantId' field.
      * @param value The value of 'restaurantId'.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder setPharmacyId(java.lang.String value) {
      validate(fields()[2], value);
      this.pharmacyId = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'restaurantId' field has been set.
      * @return True if the 'restaurantId' field has been set, false otherwise.
      */
    public boolean hasPharmacyId() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'pharmacyId' field.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder clearPharmacyId() {
      pharmacyId = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'orderId' field.
      * @return The value.
      */
    public java.lang.String getOrderId() {
      return orderId;
    }


    /**
      * Sets the value of the 'orderId' field.
      * @param value The value of 'orderId'.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder setOrderId(java.lang.String value) {
      validate(fields()[3], value);
      this.orderId = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'orderId' field has been set.
      * @return True if the 'orderId' field has been set, false otherwise.
      */
    public boolean hasOrderId() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'orderId' field.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder clearOrderId() {
      orderId = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'restaurantOrderStatus' field.
      * @return The value.
      */
    public PharmacyOrderStatus getPharmacyOrderStatus() {
      return pharmacyOrderStatus;
    }


    /**
      * Sets the value of the 'restaurantOrderStatus' field.
      * @param value The value of 'restaurantOrderStatus'.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder setPharmacyOrderStatus(PharmacyOrderStatus value) {
      validate(fields()[4], value);
      this.pharmacyOrderStatus = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'restaurantOrderStatus' field has been set.
      * @return True if the 'restaurantOrderStatus' field has been set, false otherwise.
      */
    public boolean hasPharmacyOrderStatus() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'restaurantOrderStatus' field.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder clearPharmacyOrderStatus() {
      pharmacyOrderStatus = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'products' field.
      * @return The value.
      */
    public java.util.List<Remedy> getRemedies() {
      return remedies;
    }


    /**
      * Sets the value of the 'products' field.
      * @param value The value of 'products'.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder setRemedies(java.util.List<Remedy> value) {
      validate(fields()[5], value);
      this.remedies = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'products' field has been set.
      * @return True if the 'products' field has been set, false otherwise.
      */
    public boolean hasRemedies() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'products' field.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder clearProducts() {
      remedies = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'price' field.
      * @return The value.
      */
    public java.math.BigDecimal getPrice() {
      return price;
    }


    /**
      * Sets the value of the 'price' field.
      * @param value The value of 'price'.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder setPrice(java.math.BigDecimal value) {
      validate(fields()[6], value);
      this.price = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'price' field has been set.
      * @return True if the 'price' field has been set, false otherwise.
      */
    public boolean hasPrice() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'price' field.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder clearPrice() {
      price = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'createdAt' field.
      * @return The value.
      */
    public java.time.Instant getCreatedAt() {
      return createdAt;
    }


    /**
      * Sets the value of the 'createdAt' field.
      * @param value The value of 'createdAt'.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder setCreatedAt(java.time.Instant value) {
      validate(fields()[7], value);
      this.createdAt = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'createdAt' field has been set.
      * @return True if the 'createdAt' field has been set, false otherwise.
      */
    public boolean hasCreatedAt() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'createdAt' field.
      * @return This builder.
      */
    public PharmacyApprovalRequestAvroModel.Builder clearCreatedAt() {
      fieldSetFlags()[7] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PharmacyApprovalRequestAvroModel build() {
      try {
        PharmacyApprovalRequestAvroModel record = new PharmacyApprovalRequestAvroModel();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.String) defaultValue(fields()[0]);
        record.sagaId = fieldSetFlags()[1] ? this.sagaId : (java.lang.String) defaultValue(fields()[1]);
        record.pharmacyId = fieldSetFlags()[2] ? this.pharmacyId : (java.lang.String) defaultValue(fields()[2]);
        record.orderId = fieldSetFlags()[3] ? this.orderId : (java.lang.String) defaultValue(fields()[3]);
        record.pharmacyOrderStatus = fieldSetFlags()[4] ? this.pharmacyOrderStatus : (PharmacyOrderStatus) defaultValue(fields()[4]);
        record.remedies = fieldSetFlags()[5] ? this.remedies : (java.util.List<Remedy>) defaultValue(fields()[5]);
        record.price = fieldSetFlags()[6] ? this.price : (java.math.BigDecimal) defaultValue(fields()[6]);
        record.createdAt = fieldSetFlags()[7] ? this.createdAt : (java.time.Instant) defaultValue(fields()[7]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<PharmacyApprovalRequestAvroModel>
    WRITER$ = (org.apache.avro.io.DatumWriter<PharmacyApprovalRequestAvroModel>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<PharmacyApprovalRequestAvroModel>
    READER$ = (org.apache.avro.io.DatumReader<PharmacyApprovalRequestAvroModel>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}









