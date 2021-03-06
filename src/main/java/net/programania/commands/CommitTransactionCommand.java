package net.programania.commands;

import net.programania.aggregates.TransactionAggregate;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CommitTransactionCommand {
  @TargetAggregateIdentifier
  private final String uuid;
  private final TransactionAggregate.Status status;

  public CommitTransactionCommand(String uuid) {
    this.uuid = uuid;
    this.status = TransactionAggregate.Status.COMMITTED;
  }

  public String getUuid() {
    return uuid;
  }

  public TransactionAggregate.Status getStatus() {
    return status;
  }
}
