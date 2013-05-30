package net.programania.aggregates;

import net.programania.commands.CommitTransactionCommand;
import net.programania.commands.CreateTransactionCommand;
import net.programania.commands.RollBackTransactionCommand;
import net.programania.events.TransactionCommitedEvent;
import net.programania.events.TransactionCreatedEvent;
import net.programania.events.TransactionRolledBackEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class TransactionAggregate extends AbstractAnnotatedAggregateRoot {
  private static final Boolean lock = false;
  private static int sequence = 0;
  @AggregateIdentifier
  private String uuid;
  private Status status;

  public TransactionAggregate() {
  }

  @CommandHandler
  public TransactionAggregate(CreateTransactionCommand cmd) {
    apply(new TransactionCreatedEvent(cmd.getUuid(), cmd.getStatus()));
  }

  @CommandHandler
  public void commit(CommitTransactionCommand cmd) {
    apply(new TransactionCommitedEvent(cmd.getUuid(), cmd.getStatus()));
  }

  @CommandHandler
  public void rollBack(RollBackTransactionCommand cmd) {
    apply(new TransactionRolledBackEvent(cmd.getUuid(), cmd.getStatus()));
  }

  @EventHandler
  public void on(TransactionCreatedEvent event) {
    this.uuid = event.getUuid();
    this.status = event.getStatus();
  }

  @EventHandler
  public void on(TransactionCommitedEvent event) {
    this.status = event.getStatus();
  }

  @EventHandler
  public void on(TransactionRolledBackEvent event) {
    this.status = event.getStatus();
  }

  public enum Status {
    COMMITTED, ONGOING, ROLLED_BACK;
  }
}
